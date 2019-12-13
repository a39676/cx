package demo.article.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.mapper.ArticleLongFeedbackMapper;
import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.mapper.ArticleLongReviewMapper;
import demo.article.article.pojo.constant.ArticleViewConstant;
import demo.article.article.pojo.dto.ArticleFeedbackDTO;
import demo.article.article.pojo.dto.EditArticleLongDTO;
import demo.article.article.pojo.dto.FindArticleLongByConditionDTO;
import demo.article.article.pojo.dto.ReadyToEditArticleLongDTO;
import demo.article.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyDTO;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.po.ArticleLongFeedback;
import demo.article.article.pojo.po.ArticleLongReview;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.article.article.pojo.type.ArticleChannelType;
import demo.article.article.pojo.type.ArticleFeedbackType;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.pojo.vo.ArticleLongVO;
import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleChannelService;
import demo.article.article.service.ArticleService;
import demo.article.article.service.ArticleSummaryService;
import demo.article.article.service.ArticleViewService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.user.controller.UsersController;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.tool.service.TextFilter;
import demo.tool.service.ValidRegexToolService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleServiceImpl extends ArticleCommonService implements ArticleService {

	@Autowired
	private UsersController userController;
	
	@Autowired
	private ArticleChannelService channelService;
	@Autowired
	private ArticleViewService articleViewService;
	@Autowired
	private ArticleAdminService articleAdminService;
	@Autowired
	private ArticleSummaryService summaryService;
	@Autowired
	private TextFilter textFilter;
	
	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	@Autowired
	private ArticleLongFeedbackMapper articleLongFeedbackMapper;
	
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	private String getArticleStorePrefixPath() {
		return constantService.getValByName(SystemConstantStore.articleStorePrefixPath);
	}
	
	private String getArticleSummaryStorePrefixPath() {
		return constantService.getValByName(SystemConstantStore.articleSummaryStorePrefixPath);
	}

	private Long loadMaxArticleLength() {
		Long maxArticleLength = 0L;
		try {
			String maxLengthStr = constantService.getValByName(SystemConstantStore.maxArticleLength);
			if(maxLengthStr != null) {
				maxArticleLength = Long.parseLong(maxLengthStr);
			}
		} catch (Exception e) {
			return maxArticleLength;
		}
		
		return maxArticleLength;
		
	}
	
	@Override
	public String getImageHttpUrlPattern() {
		return imageHttpUrlPattern;
	}

	@Override
	public Long decryptPrivateKey(String pk) {
		return decryptArticlePrivateKey(pk);
	}
	
	@Override
	public String encryptId(Long id) {
		return encryptArticleId(id, getCustomKey());
	}
	
	private CommonResultCX batchCreateArticleLong(Long userId, CreateArticleParam controllerParam) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		CommonResultCX result = new CommonResultCX();
		String superAdminKey = constantService.getValByName(SystemConstantStore.superAdminKey);
		if(StringUtils.isBlank(superAdminKey) 
				|| StringUtils.isBlank(controllerParam.getSuperAdminKey()) 
				|| !superAdminKey.equals(controllerParam.getSuperAdminKey())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		String oldContent = controllerParam.getContent();
		List<String> lines = Arrays.asList(oldContent.split("http"));
		if(lines.size() < 1) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		
		CommonResult tmpResult = null;
		int successCount = 0;
		for(String line : lines) {
			if(!StringUtils.isBlank(line)) {
				tmpResult = createNewArticleLong(userId, controllerParam);
				if(tmpResult.isSuccess()) {
					successCount = successCount + 1;
				}
			}
		}
		
		result.setIsSuccess();
		result.setMessage("成功分拆发表" + successCount + "份；失败" + (lines.size() - successCount) + "份");
		return result;
	}
	
	@Override
	public ModelAndView buildCreatingArticleLongView(CreatingArticleParam controllerParam) {
		ModelAndView view = null;
		controllerParam.setUserId(baseUtilCustom.getUserId());
		if(controllerParam.getUserId() == null) {
			view = new ModelAndView(BaseViewConstant.viewError);
			view.addObject("exception", ResultTypeCX.notLoginUser.getName());
			return view;
		}
		
//		String uuid = controllerParam.getUuid();
//		Integer channelId = channelService.getChannelIdByUUID(uuid);
//		if(channelId == null) {
//			return new ModelAndView(BaseViewConstant.viewError);
//		}
//		List<Integer> channelIdList = channelService.findChannelIdListByUserId(controllerParam.getUserId());
//		if(!channelIdList.contains(channelId)) {
//			return new ModelAndView(BaseViewConstant.viewError);
//		}
		
		view = new ModelAndView(ArticleViewConstant.creatingArticleLong);
		view.addObject("createNew", "true");
		return view;
	}
	
	private CommonResultCX createNewArticleLong(Long userId, CreateArticleParam controllerParam) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		CommonResultCX result = new CommonResultCX();
		int insertCount = 0;
		
		String channelIdStr = controllerParam.getChannelId();
		if(StringUtils.isBlank(channelIdStr) || !numberUtil.matchInteger(channelIdStr)) {
			log.error("creating article errorParam %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long channelId = Long.parseLong(channelIdStr);
		
		ArticleChannels channel = channelService.findArticleChannelById(channelId);
		if(channel == null || !ArticleChannelType.publicChannel.getCode().equals(channel.getChannelType())) {
			log.error("creating article checkPostLimitError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		String title = null;
		if(itIsBigUser()) {
			title = controllerParam.getTitle();
		} else {
			PolicyFactory filter = textFilter.getFilter();
			title = filter.sanitize(controllerParam.getTitle());
			controllerParam.setContent(filter.sanitize(controllerParam.getContent()));
		}
		
		if(StringUtils.isBlank(title)) {
			result.failWithMessage("请输入标题");
			return result;
		}

		Long maxArticleLength = loadMaxArticleLength();
		String summaryStorePrefixPath = getArticleSummaryStorePrefixPath();
		String storePrefixPath = getArticleStorePrefixPath();
		if (StringUtils.isAnyBlank(summaryStorePrefixPath, storePrefixPath) || maxArticleLength < 1) {
			log.error("creating article serviceError  articleStorePrefixPath %s, maxArticleLength: %s, userId: %s", storePrefixPath, maxArticleLength, userId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		ArticleLong newArticle = new ArticleLong();

		ArticleFileSaveResult saveArticleResult = saveArticleFile(storePrefixPath, userId, controllerParam.getContent());
		if (!saveArticleResult.isSuccess()) {
			return saveArticleResult;
		}
		
		String filePath = saveArticleResult.getFilePath();

		Long newArticleId = snowFlake.getNextId();
		newArticle.setArticleId(newArticleId);
		newArticle.setArticleTitle(title);
		newArticle.setPath(filePath);
		newArticle.setUserId(userId);
		newArticle.setChannelId(channelId);
		
		try {
			insertCount = articleLongMapper.insertSelective(newArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insertCount < 1) {
			log.error("creating article insertArticleLongError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if (saveArticleResult.getImageUrls() != null && saveArticleResult.getImageUrls().size() > 0) {
//			imageController.insertImageFromArticle(saveArticleResult.getImageUrls(), newArticle.getArticleId());
		}

		saveArticleResult.setArticleId(newArticleId);

		CommonResultCX saveArtieleSummaryResult = saveArticleSummaryFile(userId, newArticle.getArticleId(), title,
				saveArticleResult.getFirstLine(), saveArticleResult.getImageUrls());
		if (!saveArtieleSummaryResult.isSuccess()) {
			return saveArtieleSummaryResult;
		}

		insertCount = summaryService.insertArticleLongSummary(userId, newArticle.getArticleId(), title,
				saveArtieleSummaryResult.getMessage());
		if (insertCount < 1) {
			log.error("creating article insertArticleSummaryError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(controllerParam.getQuickPass()) {
			quickPass(newArticle.getArticleId());
		}
		
		result.fillWithResult(ResultTypeCX.createArticleLongSuccess);
		return result;
	}
	
	@Override
	public CommonResult crateArticleLongPrefixServcie(CreateArticleParam cp) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		CommonResult serviceResult = new CommonResult();
		
		if(!baseUtilCustom.isLoginUser()) {
			serviceResult.failWithMessage("请先登录");
			return serviceResult;
		}
		Long userId = baseUtilCustom.getUserId();
		if(itIsBigUser()) {
			cp.setQuickPass(true);
		} else {
			cp.setQuickPass(false);
		}
		
		if(StringUtils.isNotBlank(cp.getSuperAdminKey())) {
			serviceResult = batchCreateArticleLong(userId, cp);
		} else {
			serviceResult = createNewArticleLong(userId, cp);
		}
		return serviceResult;
	}
	
	private void quickPass(Long articleId) {
		String pk = encryptId(articleId);
		if(pk != null) {
			ReviewArticleLongParam passArticleParam = new ReviewArticleLongParam();
			passArticleParam.setPk(pk);
			passArticleParam.setReviewCode(ArticleReviewType.pass.getReviewCode());
			try {
				articleAdminService.handelReviewArticle(passArticleParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public ArticleFileSaveResult saveArticleFile(String storePrefixPath, Long userId, String content) throws IOException {
		String fileName = userId + "L" + UUID.randomUUID().toString().substring(0, 8) + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(storePrefixPath + timeFolder);
		String finalFilePath = storePrefixPath + timeFolder + "/" + fileName;
		ArticleFileSaveResult result = new ArticleFileSaveResult();

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.fillWithResult(ResultTypeCX.serviceError);
				return result;
			}
		}

		Long maxArticleLength = loadMaxArticleLength();
		
		if (content.length() > maxArticleLength) {
			result.fillWithResult(ResultTypeCX.articleTooLong);
			return result;
		}

		if (StringUtils.isBlank(content) || content.replaceAll("\\s", "").length() < 6) {
			result.fillWithResult(ResultTypeCX.articleTooShort);
			return result;
		}

		StringBuffer sb = new StringBuffer();
		
		sb.append(content);

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}

	private CommonResultCX saveArticleSummaryFile(Long userId, Long articleId, String title, String firstLine,
			List<String> imageUrls) {
		if(StringUtils.isBlank(firstLine)) {
			firstLine = "";
		}
		CommonResultCX result = new CommonResultCX();
		if(!loadCustomKey()) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		String fileName = userId + "L" + UUID.randomUUID().toString().substring(0, 8) + ".txt";
		String timeFolder = LocalDate.now().toString();
		
		String summaryStorePrefixPath = getArticleSummaryStorePrefixPath();
		File mainFolder = new File(summaryStorePrefixPath + timeFolder);
		String finalFilePath = summaryStorePrefixPath + timeFolder + "/" + fileName;

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.fillWithResult(ResultTypeCX.serviceError);
				return result;
			}
		}

		if (firstLine.length() > 100) {
			firstLine = firstLine.substring(0, 100) + "...";
		}

		StringBuffer sb = new StringBuffer(firstLine + System.lineSeparator());

		if (imageUrls != null && imageUrls.size() > 0) {
			for (int i = 0; i < 4 && i < imageUrls.size(); i++) {
				sb.append(imageUrls.get(i) + System.lineSeparator());
			}
		}

		try {
			ioUtil.byteToFile(sb.toString().getBytes("utf8"), finalFilePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.fillWithResult(ResultTypeCX.errorWhenArticleSave);
			return result;
		}

		result.successWithMessage(finalFilePath);

		return result;
	}

	@Override
	public FindArticleLongResult findArticleLongByArticleSummaryPrivateKey(FindArticleLongByArticleSummaryPrivateKeyDTO param, HttpServletRequest request) {
		Long userId = baseUtilCustom.getUserId();

		FindArticleLongResult result = new FindArticleLongResult();
		ArticleLongVO vo = null;
		
		Long articleId = decryptArticlePrivateKey(param.getPrivateKey());
		if(articleId == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorParam.getName());
			result.setArticleLongVO(vo);
			return result;
		}
		visitDataService.insertVisitData(request, articleId.toString());
		result.setArticleId(articleId);
		
		articleViewService.insertOrUpdateViewCount(articleId);
		
		FindArticleLongByConditionDTO mapperDTO = new FindArticleLongByConditionDTO();
		BeanUtils.copyProperties(param, mapperDTO);
		mapperDTO.setArticleId(articleId);
		vo = articleLongMapper.findArticleLongByDecryptId(mapperDTO);
		
		if(vo == null) {
			result.fillWithResult(ResultTypeCX.errorWhenArticleLoad);
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorWhenArticleLoad.getName());
			result.setArticleLongVO(vo);
			return result;
		}
		
		if(vo.getIsDelete() && !baseUtilCustom.hasAdminRole()) {
			vo = new ArticleLongVO();
			vo.setContentLines("这篇文已经失踪了...请联系管理员...");
			result.setArticleLongVO(vo);
			return result;
		}
		
		fillArticleContent(vo, param.getPrivateKey(), userId);
		result.setArticleLongVO(vo);
		return result;
	}

	private void fillArticleContent(ArticleLongVO articleLongVO, String pk, Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strContent = ioUtil.getStringFromFile(articleLongVO.getPath());
		articleLongVO.setContentLines(strContent);
		articleLongVO.setCreateDateString(sdf.format(articleLongVO.getCreateTime()));
		if(articleLongVO.getEditTime() != null) {
			articleLongVO.setEditDateString(sdf.format(articleLongVO.getEditTime()));
		}
		if(userId != null && userId.equals(articleLongVO.getUserId())) {
			articleLongVO.setIWroteThis(true);
		}
		
		if(articleLongVO.getUserId() != null) {
			articleLongVO.setHeadIamgeUrl(userController.findHeadImageUrl(articleLongVO.getUserId()));
		}
		articleLongVO.setPrivateKey(pk);
	}
	
	@Override
	public boolean iWroteThis(String privateKey) {
		Long userId = baseUtilCustom.getUserId();
		if(userId == null || StringUtils.isBlank(privateKey)) {
			return false;
		}
		
		Long articleId = decryptArticlePrivateKey(privateKey);
		if(articleId == null) {
			return false;
		}
		
		if(articleLongMapper.iWroteThis(userId, articleId) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void refillArticleLongReviewCreatorId() {
		
		List<ArticleLongReview> reviewResultList = articleLongReviewMapper.findArticleCreatorIdIsNull();
		if(reviewResultList == null || reviewResultList.size() < 1) {
			return;
		}
		
		List<Long> articleIdList = reviewResultList.stream().map(ArticleLongReview::getArticleId).collect(Collectors.toList());
		
		List<ArticleLong> articleList = articleLongMapper.findArticleLongList(articleIdList);
		if(articleList == null || articleList.size() < 1) {
			return;
		}
		
		Map<Long, Long> articleIdKeyCreatorId = articleList.stream().collect(Collectors.toMap(ArticleLong::getArticleId, ArticleLong::getUserId));
		
		reviewResultList.stream().forEach(po -> po.setArticleCreatorId(articleIdKeyCreatorId.get(po.getArticleId())));
		
		articleLongReviewMapper.batchUpdateFillCreatorId(reviewResultList);
	}

	@Override
	public CommonResultCX articleLongFeedback(ArticleFeedbackDTO dto, HttpServletRequest request) {
		visitDataService.insertVisitData(request);
		CommonResultCX result = new CommonResultCX();
		
		Long userId = baseUtilCustom.getUserId();
		if (StringUtils.isBlank(dto.getPk()) || StringUtils.isBlank(dto.getFeedback())) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		String feedback = StringEscapeUtils.escapeHtml(dto.getFeedback());
		if(StringUtils.isBlank(feedback)) {
			result.failWithMessage("期待您请填写反馈内容");
			return result;
		} else if (feedback.length() > 512) {
			result.failWithMessage("反馈内容过长");
			return result;
		} else if (StringUtils.isBlank(dto.getEmail()) || !validRegexToolService.validEmail(dto.getEmail())) {
			result.failWithMessage("请输入正确的email");
			return result;
		} 
		
		Long articleId = decryptArticlePrivateKey(dto.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		
		ArticleLong article = articleLongMapper.selectByPrimaryKey(articleId);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLongFeedback feedbackPO = new ArticleLongFeedback();
		
		feedbackPO.setId(snowFlake.getNextId());
		feedbackPO.setCreateTime(LocalDateTime.now());
		feedbackPO.setFeedbackUserId(userId);
		feedbackPO.setArticleId(articleId);
		feedbackPO.setArticleCreatorId(article.getUserId());
		feedbackPO.setArticleTitle(article.getArticleTitle());
		feedbackPO.setFeedback(feedback);
		feedbackPO.setEmail(dto.getEmail());
		feedbackPO.setMobile(dto.getMobile());
		feedbackPO.setFeedbackUserNickname(dto.getNickname());
		feedbackPO.setMobile(dto.getMobile());
		feedbackPO.setFeedbackType(ArticleFeedbackType.normal.getCode());

		articleLongFeedbackMapper.insert(feedbackPO);
		
		result.fillWithResult(ResultTypeCX.feedbackReciveSuccess);
		return result;
	}
	
	private boolean itIsBigUser() {
		return baseUtilCustom.hasAnyRole(
				RolesType.ROLE_POSTER.getName(),
				RolesType.ROLE_ADMIN.getName(),
				RolesType.ROLE_SUPER_ADMIN.getName());
	}

	@Override
	public ModelAndView readyToEditArticleLong(ReadyToEditArticleLongDTO dto) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.creatingArticleLong);;
		
		ArticleLongVO vo = findArticleLongVOForEdit(dto);
		
		view.addObject("articleVO", vo);
		view.addObject("edit", "true");
		
		return view;
	}
	
	private ArticleLongVO findArticleLongVOForEdit(ReadyToEditArticleLongDTO dto) {
		ArticleLongVO vo;
		
		if(StringUtils.isBlank(dto.getPrivateKey())) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorParam.getName());
			return vo;
		}
		
		Long articleId = decryptArticlePrivateKey(dto.getPrivateKey());
		
		if(articleId == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorParam.getName());
			return vo;
		}
		
		FindArticleLongByConditionDTO mapperDTO = new FindArticleLongByConditionDTO();
		BeanUtils.copyProperties(dto, mapperDTO);
		mapperDTO.setArticleId(articleId);
		vo = articleLongMapper.findArticleLongByDecryptId(mapperDTO);
		
		if(vo == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorWhenArticleLoad.getName());
			return vo;
		}
		
		Long userId = baseUtilCustom.getUserId();
		boolean adminFlag = baseUtilCustom.hasAdminRole();
		if(vo.getUserId() != userId && !adminFlag) {
			vo = new ArticleLongVO();
			vo.setContentLines("没有编辑其他用户的文章的权限");
			return vo;
		}
		
		if(vo.getIsDelete() && !adminFlag) {
			vo = new ArticleLongVO();
			vo.setContentLines("这篇文已经失踪了...请联系管理员...");
			return vo;
		}
		
		fillArticleContent(vo, dto.getPrivateKey(), userId);
		
		return vo;
	}
	
	public void editArticleLongHandler(EditArticleLongDTO dto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		/*
		 * TODO
		 * 编辑文章时, 还需要鉴定一次权限 ---> (管理员, 本人?)
		 * 记录编辑历史 ---> 次数, 最后编辑时间, 原文上直接修改???? 新建并保留原文?
		 */
		if(StringUtils.isBlank(dto.getPrivateKey())) {
//			TODO
		}
		
		Long articleId = decryptArticlePrivateKey(dto.getPrivateKey());
		
		if(articleId == null) {
//			TODO
		}
		
		ArticleLong sourcePO = articleLongMapper.selectByPrimaryKey(articleId);
		
		boolean adminFlag = baseUtilCustom.hasAdminRole();
		Long userId = baseUtilCustom.getUserId();
		if(!sourcePO.getUserId().equals(userId) || !adminFlag) {
//			TODO
		}
		
		ArticleLong newPO = new ArticleLong();
		sourcePO.setIsEdited(true);
		BeanUtils.copyProperties(sourcePO, newPO);
		
		articleLongMapper.updateByPrimaryKey(sourcePO);
		
		newPO.setEditOf(sourcePO.getArticleId());
		newPO.setEditBy(userId);
		if(sourcePO.getEditCount() == null) {
			newPO.setEditCount(1);
		} else {
			newPO.setEditCount(sourcePO.getEditCount() + 1);
		}
		newPO.setEditTime(LocalDateTime.now());

		CreateArticleParam p = new CreateArticleParam();
		p.setContent(dto.getContent());
		p.setTitle(dto.getTitle());
		p.setChannelId(dto.getChannelId());
		if(itIsBigUser()) {
			p.setQuickPass(true);
		}
		createNewArticleLong(userId, p);
		
		
		/*
		 * TODO
		 */
//		articleLongMapper.insertSelective(newPO);
	}
}