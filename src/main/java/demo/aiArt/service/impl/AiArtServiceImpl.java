package demo.aiArt.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.mapper.AiArtGeneratingRecordMapper;
import demo.aiArt.mapper.AiArtTextToImageJobRecordMapper;
import demo.aiArt.mq.producer.AiArtTextToImageProducer;
import demo.aiArt.pojo.dto.TextToImageDTO;
import demo.aiArt.pojo.po.AiArtGeneratingRecord;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.aiArt.pojo.result.TextToImageFromApiResult;
import demo.aiArt.pojo.type.AiArtJobType;
import demo.aiArt.service.AiArtCommonService;
import demo.aiArt.service.AiArtService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class AiArtServiceImpl extends AiArtCommonService implements AiArtService {

	@Autowired
	private AiArtUtil aiArtUtil;
	@Autowired
	private AiArtGeneratingRecordMapper aiArtGeneratingRecordMapper;
	@Autowired
	private AiArtTextToImageJobRecordMapper aiArtTextToImageJobRecordMapper;
	@Autowired
	private AiArtTextToImageProducer aiArtTextToImageProducer;
	@Autowired
	private FileUtilCustom fileUtilCustom;

	@Override
	public CommonResult sendDtoToMq(TextToImageDTO dto) {
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
			if (dto.getWedith() > aiArtOptionService.getMaxHeight()) {
				dto.setWedith(aiArtOptionService.getMaxHeight());
			}
		}
		if (dto.getHeight() != null && dto.getHeight() > 0) {
			if (dto.getHeight() > aiArtOptionService.getMaxWidth()) {
				dto.setHeight(aiArtOptionService.getMaxWidth());
			}
		}
		if (dto.getCfgScale() != null && dto.getCfgScale() > 0) {
			if (dto.getCfgScale() > aiArtOptionService.getMaxBatch()) {
				dto.setCfgScale(aiArtOptionService.getMaxBatch());
			}
		}
		if (dto.getSteps() != null && dto.getSteps() > 0) {
			if (dto.getSteps() > aiArtOptionService.getMaxCfgScale()) {
				dto.setSteps(aiArtOptionService.getMaxCfgScale());
			}
		}
		if (dto.getBatchSize() != null) {
			if (dto.getBatchSize() > aiArtOptionService.getMaxSteps()) {
				dto.setBatchSize(aiArtOptionService.getMaxSteps());
			}
			if (dto.getBatchSize() < 0) {
				dto.setBatchSize(1);
			}
		}

		Long jobId = snowFlake.getNextId();
		dto.setJobId(jobId);
		AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
		row.setAiUserId(aiChatUserId);
		row.setId(jobId);
		row.setJobStatus(AiArtJobType.WAITING.getCode().byteValue());
		aiArtTextToImageJobRecordMapper.insertSelective(row);

		r = saveTextToImgParameterDTO(dto);
		if (r.isFail()) {
			return r;
		}

		aiArtTextToImageProducer.send(jobId);

		return r;
	}

	private CommonResult saveTextToImgParameterDTO(TextToImageDTO dto) {
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
	public TextToImageFromApiResult txtToImgByJobId(Long jobId) {
		String parameterPathStr = getParameterPathByJobId(jobId);
		String content = fileUtilCustom.getStringFromFile(parameterPathStr);
		TextToImageDTO dto = buildObjFromJsonCustomization(content, TextToImageDTO.class);
		TextToImageFromApiResult txtToImgResult = sendTxtToImgRequest(dto);
		return txtToImgResult;
	}

	private TextToImageFromApiResult sendTxtToImgRequest(TextToImageDTO dto) {
		TextToImageFromApiResult r = new TextToImageFromApiResult();

		JSONObject responseJson = aiArtUtil.sendTxtToImgRequest(dto);
		if(responseJson == null || !responseJson.containsKey("images")) {
			AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
			row.setId(dto.getJobId());
			row.setJobStatus(AiArtJobType.FAILED.getCode().byteValue());
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
			r.setMessage("Can NOT create image, please try again later");
			return r;
		}

		JSONArray imageListInBase64 = responseJson.getJSONArray("images");

		List<String> imageList = new ArrayList<>();
		for (int i = 0; i < imageListInBase64.size(); i++) {
			imageList.add(imageListInBase64.getString(i));
			Long newImgId = saveImg(imageListInBase64.getString(i), dto);
			if (newImgId == null) {
				AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
				row.setId(dto.getJobId());
				row.setJobStatus(AiArtJobType.FAILED.getCode().byteValue());
				aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
				r.setMessage("Can NOT create image, please try again later");
				return r;
			}
		}
		
		AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
		row.setId(dto.getJobId());
		row.setJobStatus(AiArtJobType.SUCCESS.getCode().byteValue());
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
		
		r.setImageList(imageList);
		r.setIsSuccess();
		return r;

	}

	private Long saveImg(String data, TextToImageDTO dto) {
		Long newImgId = snowFlake.getNextId();
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
		BufferedImage img;
		try {
			img = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			sendTelegramMessage("AI生成图片异常, 无法转化图片成 BufferedImage: " + e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

		String imgSavePath = aiArtOptionService.getImageSavingFolder() + "/" + dto.getJobId() + "/" + newImgId + ".jpg";
		String settingSavePath = aiArtOptionService.getImageSavingFolder() + "/" + dto.getJobId() + "/" + newImgId
				+ ".json";
		File outputImg = new File(imgSavePath);
		File outputSettings = new File(settingSavePath);

		JSONObject settingsJson = JSONObject.fromObject(dto);

		File parentFolder = outputImg.getParentFile();
		if (!parentFolder.exists()) {
			if (!parentFolder.mkdirs()) {
				sendTelegramMessage("AI生成图片异常, 无法创建保存图片文件夹: " + parentFolder.getAbsolutePath());
				return null;
			}
		}

		try {
			ImageIO.write(img, "jpg", outputImg);
			FileUtils.writeByteArrayToFile(outputSettings, settingsJson.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			sendTelegramMessage("AI生成图片异常, 无法保存图片: " + e.getLocalizedMessage());
			return null;
		}

		AiArtGeneratingRecord row = new AiArtGeneratingRecord();
		row.setAiUserId(dto.getJobId());
		row.setId(newImgId);
//		row.setTokens(null); // TODO  how to set used tokens
		aiArtGeneratingRecordMapper.insertSelective(row);

		return newImgId;
	}

}
