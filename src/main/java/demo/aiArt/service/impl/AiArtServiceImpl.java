package demo.aiArt.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageFromDTO;
import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.mapper.AiArtGeneratingRecordMapper;
import demo.aiArt.mapper.AiArtTextToImageJobRecordMapper;
import demo.aiArt.mq.producer.AiArtTextToImageProducer;
import demo.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.aiArt.pojo.po.AiArtGeneratingRecord;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecordExample;
import demo.aiArt.pojo.result.SendTextToImgJobResult;
import demo.aiArt.service.AiArtCommonService;
import demo.aiArt.service.AiArtService;
import demo.aiChat.pojo.po.AiChatUserDetail;
import demo.aiChat.service.AiChatUserService;
import demo.common.pojo.dto.BaseDTO;
import demo.image.pojo.type.ImageTagType;
import demo.image.service.ImageService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class AiArtServiceImpl extends AiArtCommonService implements AiArtService {

	@Autowired
	private AiArtColabUtil aiArtUtil;
	@Autowired
	private AiArtGeneratingRecordMapper aiArtGeneratingRecordMapper;
	@Autowired
	private AiArtTextToImageJobRecordMapper aiArtTextToImageJobRecordMapper;
	@Autowired
	private AiArtTextToImageProducer aiArtTextToImageProducer;
	@Autowired
	private FileUtilCustom fileUtilCustom;
	@Autowired
	private ImageService imageService;
	@Autowired
	private AiChatUserService aiChatUserService;

	@Override
	public CommonResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getTmpKey())) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(dto.getTmpKey());
		if (aiChatUserId == null) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		return sendTextToImgDtoToMq(aiChatUserId, dto);
	}

	@Override
	public SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();
		if (StringUtils.isBlank(dto.getApiKey())) {
			r.setMessage("请输入正确 API key");
			return r;
		}

		Long aiChatUserId = getAiUserIdByApiKey(dto.getApiKey());
		if (aiChatUserId == null) {
			r.setMessage("请输入正确 API key");
			return r;
		}

		return sendTextToImgDtoToMq(aiChatUserId, dto);
	}

	private SendTextToImgJobResult sendTextToImgDtoToMq(Long aiUserId, TextToImageFromDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();

		Integer jobCounting = getJobCounting(aiUserId);
		if (jobCounting >= aiArtOptionService.getMaxDailyFreeJobCount()) {
			AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiUserId);
			int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();
			if (totalAmount <= 0) {
				r.setMessage("余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
				return r;
			}
		}

		if (StringUtils.isBlank(dto.getPrompts())) {
			r.setMessage("请输入 prompts");
			return r;
		}

		if (dto.getPrompts().length() + dto.getNegativePrompts().length() > aiArtOptionService.getMaxPromptLength()) {
			r.setMessage("Too much prompts, prompts + nagative prompts should less than "
					+ aiArtOptionService.getMaxPromptLength());
			return r;
		}

		if (dto.getWedith() != null && dto.getWedith() > 0) {
			if (dto.getWedith() > aiArtOptionService.getMaxWidth()) {
				dto.setWedith(aiArtOptionService.getMaxWidth());
			}
		}
		if (dto.getHeight() != null && dto.getHeight() > 0) {
			if (dto.getHeight() > aiArtOptionService.getMaxHeight()) {
				dto.setHeight(aiArtOptionService.getMaxHeight());
			}
		}
		if (dto.getCfgScale() != null && dto.getCfgScale() > 0) {
			if (dto.getCfgScale() > aiArtOptionService.getMaxCfgScale()) {
				dto.setCfgScale(aiArtOptionService.getMaxCfgScale());
			}
		}
		if (dto.getSteps() != null && dto.getSteps() > 0) {
			if (dto.getSteps() > aiArtOptionService.getMaxSteps()) {
				dto.setSteps(aiArtOptionService.getMaxSteps());
			}
		}
		if (dto.getBatchSize() != null) {
			if (dto.getBatchSize() > aiArtOptionService.getMaxBatch()) {
				dto.setBatchSize(aiArtOptionService.getMaxBatch());
			}
			if (dto.getBatchSize() < 0) {
				dto.setBatchSize(1);
			}
		}

		Long jobId = snowFlake.getNextId();
		dto.setJobId(jobId);
		AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
		row.setAiUserId(aiUserId);
		row.setId(jobId);
		row.setJobStatus(AiArtJobStatusType.WAITING.getCode().byteValue());
		aiArtTextToImageJobRecordMapper.insertSelective(row);

		CommonResult saveParameterResult = saveTextToImgParameterDTO(dto);
		if (saveParameterResult.isFail()) {
			r.setMessage(saveParameterResult.getMessage());
			return r;
		}

		aiArtTextToImageProducer.send(jobId);
		r.setJobPk(systemOptionService.encryptId(jobId));
		r.setIsSuccess();

		return r;
	}

	private CommonResult saveTextToImgParameterDTO(TextToImageFromDTO dto) {
		CommonResult r = new CommonResult();
		String folderPathStr = aiArtOptionService.getTextToImageParameterSavingFolder();
		File mainFolder = new File(folderPathStr);
		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				r.setMessage("Can NOT create parameter saving folder");
				return r;
			}
		}

		JSONObject settingsJson = JSONObject.fromObject(dto);

		File outputSettings = new File(folderPathStr + File.separator + dto.getJobId() + ".json");

		try {
			FileUtils.writeByteArrayToFile(outputSettings, settingsJson.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			r.setMessage("Text to image parameter cache error: " + e.getLocalizedMessage());
			e.printStackTrace();
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private String getParameterPathByJobId(Long jobId) {
		File outputSettings = new File(
				aiArtOptionService.getTextToImageParameterSavingFolder() + File.separator + jobId + ".json");
		if (!outputSettings.exists()) {
			return null;
		}
		return outputSettings.getAbsolutePath();
	}

	@Override
	public CommonResult txtToImgByJobId(Long jobId) {
		AiArtTextToImageJobRecord po = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		if (po == null || AiArtJobStatusType.SUCCESS.getCode().byteValue() == po.getJobStatus()) {
			CommonResult r = new CommonResult();
			r.setMessage("Job had done");
			return r;
		}
		String parameterPathStr = getParameterPathByJobId(jobId);
		String content = fileUtilCustom.getStringFromFile(parameterPathStr);
		TextToImageFromWechatDTO dto = buildObjFromJsonCustomization(content, TextToImageFromWechatDTO.class);
		CommonResult txtToImgResult = null;
		if (systemOptionService.isDev()) {
			txtToImgResult = sendTxtToImgRequestWhenDev(dto);
		} else {
			txtToImgResult = sendTxtToImgRequest(dto);
		}

		if (txtToImgResult.isSuccess()) {
			AiArtGeneratingRecord imgGeneratingRecord = new AiArtGeneratingRecord();
			imgGeneratingRecord.setAiUserId(po.getAiUserId());
			imgGeneratingRecord.setId(dto.getJobId());
			imgGeneratingRecord.setTokens(new BigDecimal(dto.getWedith().longValue() * dto.getHeight().longValue()
					* dto.getSteps().longValue() * dto.getBatchSize() / 50000));
			aiArtGeneratingRecordMapper.insertSelective(imgGeneratingRecord);

			Integer jobCounting = getJobCounting(po.getAiUserId());
			if (jobCounting > aiArtOptionService.getMaxDailyFreeJobCount()) {
				aiChatUserService.__debitAmountAndAddTokenUsage(po.getAiUserId(), imgGeneratingRecord.getTokens());
			}
		}

		return txtToImgResult;
	}

	private CommonResult sendTxtToImgRequestWhenDev(TextToImageFromDTO dto) {
		AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		row.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		row.setRunCount(row.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);

		List<String> imageUrlList = new ArrayList<>();
		for (int i = 0; i < dto.getBatchSize(); i++) {
			imageUrlList.add(
					"http://localhost:10001/image/getImage/?imgPK=gw%2Fwq6%2BgbZ2IZkn1CJ%2Bdm7%2BwwR5sL%2Bta0qgCzZ1YJIw%3D");
		}
		saveResultJson(dto, imageUrlList);

		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	private CommonResult sendTxtToImgRequest(TextToImageFromDTO dto) {
		CommonResult r = new CommonResult();

		JSONObject responseJson = aiArtUtil.sendTxtToImgRequest(dto);
		if (responseJson == null || !responseJson.containsKey("images")) {
			AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
			row.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
			row.setRunCount(row.getRunCount() + 1);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
			r.setMessage("Can NOT create image, please try again later");
			return r;
		}

		JSONArray imageListInBase64 = responseJson.getJSONArray("images");

		List<String> imageUrlList = new ArrayList<>();
		for (int i = 0; i < imageListInBase64.size(); i++) {
			ImageSavingResult imgSavingResult = saveImg(imageListInBase64.getString(i));
			if (imgSavingResult.isFail()) {
				AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
				row.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
				row.setRunCount(row.getRunCount() + 1);
				aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
				r.setMessage("Can NOT create image, please try again later");
				return r;
			}
			imageUrlList.add(imgSavingResult.getImgUrl());
		}

		r = saveResultJson(dto, imageUrlList);

		AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		row.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		row.setRunCount(row.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);

		addJobCounting(row.getAiUserId());

		r.setIsSuccess();
		return r;

	}

	private ImageSavingResult saveImg(String data) {
		ImageSavingTransDTO imgSavingDTO = new ImageSavingTransDTO();
		imgSavingDTO.setImgBase64Str(data);
		imgSavingDTO.setImgName(snowFlake.getNextId() + ".jpg");
		imgSavingDTO.setImgTagCode(ImageTagType.AI_ART.getCode());
		ImageSavingResult imgSavingResult = imageService.imageSaving(imgSavingDTO);

		if (imgSavingResult.isFail()) {
			sendTelegramMessage("AI生成图片异常, 无法保存图片: " + imgSavingResult.getMessage());
		}

		return imgSavingResult;
	}

	private CommonResult saveResultJson(TextToImageFromDTO dto, List<String> imgUrlList) {
		CommonResult r = new CommonResult();
		AiArtGenerateImageResult result = new AiArtGenerateImageResult();
		result.setParameter(dto);
		result.setImgUrlList(imgUrlList);

		String resultJsonSavePath = getJobResultStrPath(dto.getJobId());
		File resultJsonFile = new File(resultJsonSavePath);

		File parentFolder = new File(aiArtOptionService.getGenerateImageResultFolder());
		if (!parentFolder.exists()) {
			if (!parentFolder.mkdirs()) {
				sendTelegramMessage("AI生成图片异常, 无法创建保存设定数据文件夹: " + parentFolder.getAbsolutePath());
				return r;
			}
		}

		JSONObject resultJson = JSONObject.fromObject(result);
		try {
			FileUtils.writeByteArrayToFile(resultJsonFile, resultJson.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			sendTelegramMessage("AI生成图片异常, 无法保存设定数据: " + e.getLocalizedMessage());
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public void rerun() {
		if (!aiArtOptionService.getIsRunning()) {
			return;
		}
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria()
				.andJobStatusIn(Arrays.asList(AiArtJobStatusType.WAITING.getCode().byteValue(),
						AiArtJobStatusType.FAILED.getCode().byteValue()))
				.andIsDeleteEqualTo(false).andRunCountLessThan(aiArtOptionService.getMaxFailCountForJob());

		List<AiArtTextToImageJobRecord> poList = aiArtTextToImageJobRecordMapper.selectByExample(example);
		if (poList.isEmpty()) {
			return;
		}
		for (AiArtTextToImageJobRecord po : poList) {
			aiArtTextToImageProducer.send(po.getId());
		}
	}

	@Override
	public GetJobResultList getJobResultListByTmpKey(String userTmpKey) {
		GetJobResultList r = new GetJobResultList();
		if (StringUtils.isBlank(userTmpKey)) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(userTmpKey);
		if (aiChatUserId == null) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime minCreateTime = now.minusDays(aiArtOptionService.getMaxJobLivingDay());
		example.createCriteria().andAiUserIdEqualTo(aiChatUserId).andCreateTimeGreaterThan(minCreateTime)
				.andIsDeleteEqualTo(false);
		example.setOrderByClause("create_time desc");
		RowBounds rowBounds = new RowBounds(0, aiArtOptionService.getMaxShowJob());
		List<AiArtTextToImageJobRecord> jobResultPoList = aiArtTextToImageJobRecordMapper
				.selectByExampleWithRowbounds(example, rowBounds);
		List<AiArtGenerateImageVO> voList = new ArrayList<>();
		AiArtGenerateImageVO vo = null;
		for (AiArtTextToImageJobRecord po : jobResultPoList) {
			vo = buildAiArtGenerateImageVO(po, systemOptionService.encryptId(po.getId()));
			voList.add(vo);
		}
		r.setJobResultList(voList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetJobResultList getJobResultVoByPk(BaseDTO dto) {
		GetJobResultList r = new GetJobResultList();
		if (StringUtils.isBlank(dto.getPk())) {
			r.setMessage("Please fill the job PK");
			return r;
		}

		Long jobId = systemOptionService.decryptPrivateKey(dto.getPk());
		if (jobId == null) {
			r.setMessage("Please fill correct job PK");
			return r;
		}

		AiArtTextToImageJobRecord po = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		if (po == null) {
			r.setMessage("Job expired or NOT exists");
			return r;
		}

		AiArtGenerateImageVO vo = buildAiArtGenerateImageVO(po, dto.getPk());
		r.setJobResultList(new ArrayList<>());
		r.getJobResultList().add(vo);
		r.setIsSuccess();
		return r;
	}

	private AiArtGenerateImageVO buildAiArtGenerateImageVO(AiArtTextToImageJobRecord po, String jobPk) {
		AiArtGenerateImageVO vo = new AiArtGenerateImageVO();
		AiArtGenerateImageResult subResult = getJobResult(po.getId());
		vo = new AiArtGenerateImageVO();
		vo.setJobStatus(po.getJobStatus().intValue());
		vo.setRunCount(po.getRunCount());
		vo.setJobPk(jobPk);

		if (subResult != null) {
			List<String> imgUrlList = new ArrayList<>();
			String hostname = findHostnameFromRequest();
			if (!subResult.getImgUrlList().isEmpty()) {
				for (String imgUrl : subResult.getImgUrlList()) {
					imgUrlList.add("https://" + hostname + imgUrl);
				}
			}
			vo.setImgUrlList(imgUrlList);
			TextToImageFromDTO subParam = subResult.getParameter();
			subParam.setJobId(null);
			vo.setParameter(subParam);
		}
		return vo;
	}

	private AiArtGenerateImageResult getJobResult(Long jobId) {
		String resultJsonSavePath = getJobResultStrPath(jobId);
		String content = fileUtilCustom.getStringFromFile(resultJsonSavePath);
		AiArtGenerateImageResult result = buildObjFromJsonCustomization(content, AiArtGenerateImageResult.class);
		return result;
	}

	@Override
	public void deleteParameterFile() {
		Set<Long> targetIdSet = new HashSet<>();
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria().andJobStatusEqualTo(AiArtJobStatusType.SUCCESS.getCode().byteValue())
				.andCreateTimeGreaterThan(LocalDateTime.now().minusDays(3));
		List<AiArtTextToImageJobRecord> list = aiArtTextToImageJobRecordMapper.selectByExample(example);
		for (AiArtTextToImageJobRecord po : list) {
			targetIdSet.add(po.getId());
		}
		example.createCriteria().andRunCountGreaterThanOrEqualTo(aiArtOptionService.getMaxFailCountForJob())
				.andCreateTimeGreaterThan(LocalDateTime.now().minusDays(3));
		list = aiArtTextToImageJobRecordMapper.selectByExample(example);
		for (AiArtTextToImageJobRecord po : list) {
			targetIdSet.add(po.getId());
		}

		if (targetIdSet.isEmpty()) {
			return;
		}

		String resultJsonSavePath = null;
		File file = null;
		for (Long id : targetIdSet) {
			resultJsonSavePath = getJobResultStrPath(id);
			file = new File(resultJsonSavePath);
			if (file.exists()) {
				file.deleteOnExit();
			}
		}
	}

	private String getJobResultStrPath(Long jobId) {
		String resultJsonSavePath = aiArtOptionService.getGenerateImageResultFolder() + "/" + jobId + ".json";
		return resultJsonSavePath;
	}
}
