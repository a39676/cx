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
import demo.article.article.pojo.result.CheckParamBeforeEditArticleResult;
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
		return editArticleLong(userId, controllerParam, null);
	}
	
	private CommonResultCX editArticleLong(Long editorId, CreateArticleParam controllerParam, Long editedArticleId) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		CommonResultCX result = new CommonResultCX();
		boolean editFlag = (editedArticleId == null || editorId == null);
		
		CheckParamBeforeEditArticleResult checkResult = checkParamBeforeEditArticle(editorId, controllerParam);
		if(!checkResult.isSuccess()) {
			result.failWithMessage(checkResult.getMessage());
			return result;
		}
		
		String summaryStorePrefixPath = checkResult.getSummaryStorePrefixPath();
		String storePrefixPath = checkResult.getStorePrefixPath();
		String title = checkResult.getTitle();
		Long channelId = checkResult.getChannelId();
		
		ArticleLong editedArticlePO = null;
		if(editFlag) {
			editedArticlePO = articleLongMapper.selectByPrimaryKey(editedArticleId);
			if(editedArticlePO == null) {
				result.failWithMessage("参数异常");
				return result;
			}
			boolean adminFlag = baseUtilCustom.hasAdminRole();
			if(!editedArticlePO.getUserId().equals(editorId) || !adminFlag) {
				result.failWithMessage("无权编辑");
				return result;
			}
		}
		
		ArticleFileSaveResult saveArticleResult = saveArticleFile(storePrefixPath, editorId, controllerParam.getContent());
		if (!saveArticleResult.isSuccess()) {
			result.failWithMessage(saveArticleResult.getMessage());
			return result;
		}
		
		String newFilePath = saveArticleResult.getFilePath();

		ArticleLong newArticle = new ArticleLong();
		Long newArticleId = snowFlake.getNextId();
		newArticle.setArticleId(newArticleId);
		newArticle.setArticleTitle(title);
		newArticle.setPath(newFilePath);
		newArticle.setChannelId(channelId);
		if(editFlag) {
			newArticle.setIsEdited(true);
			newArticle.setPath(editedArticlePO.getPath());
			newArticle.setUserId(editedArticlePO.getUserId());
			result = updateEditedArticleLong(editedArticlePO, newArticleId, editorId, newFilePath);
			if(!result.isSuccess()) {
				return result;
			}
		} else {
			newArticle.setUserId(editorId);
		}
		
		int insertCount = 0;
		try {
			insertCount = articleLongMapper.insertSelective(newArticle);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insertCount < 1) {
			log.error("creating article insertArticleLongError %s, userId: %s", controllerParam.toString(), editorId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		saveArticleResult.setArticleId(newArticleId);

		CommonResultCX saveArtieleSummaryResult = saveArticleSummaryFile(editorId, newArticle.getArticleId(), title,
				saveArticleResult.getFirstLine(), saveArticleResult.getImageUrls(), summaryStorePrefixPath);
		if (!saveArtieleSummaryResult.isSuccess()) {
			result.failWithMessage(saveArtieleSummaryResult.getMessage());
			return result;
		}

		insertCount = summaryService.insertArticleLongSummary(newArticle.getUserId(), newArticle.getArticleId(), title,
				saveArtieleSummaryResult.getMessage());
		if (insertCount < 1) {
			log.error("creating article insertArticleSummaryError %s, userId: %s", controllerParam.toString(), editorId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(controllerParam.getQuickPass()) {
			if(editFlag) {
				quickPass(editedArticleId);
			} else {
				quickPass(newArticle.getArticleId());
			}
		}
		
		result.fillWithResult(ResultTypeCX.editArticleLongSuccess);
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
		
		serviceResult = createNewArticleLong(userId, cp);
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
	public ArticleFileSaveResult saveArticleFile(String storePrefixPath, Long creatorId, String content) throws IOException {
		String fileName = creatorId + "L" + UUID.randomUUID().toString().substring(0, 8) + ".txt";
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

	/**
	 * 用于保存新建/编辑的摘要文档
	 * 在编辑情况下, userID 是编辑者ID, 不一定是原作者ID, 用于命名文件名
	 * @param userId
	 * @param articleId
	 * @param title
	 * @param firstLine
	 * @param imageUrls
	 * @param summaryStorePrefixPath
	 * @return
	 */
	private CommonResultCX saveArticleSummaryFile(Long userId, Long articleId, String title, String firstLine,
			List<String> imageUrls, String summaryStorePrefixPath) {
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
	
	@Override
	public CommonResultCX editArticleLongHandler(EditArticleLongDTO dto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(dto.getPrivateKey())) {
			result.failWithMessage("参数缺失");
			return result;
		}
		
		Long targetArticleId = decryptArticlePrivateKey(dto.getPrivateKey());
		
		if(targetArticleId == null) {
			result.failWithMessage("参数错误");
			return result;
		}
		
		Long userId = baseUtilCustom.getUserId();

		CreateArticleParam param = new CreateArticleParam();
		param.setContent(dto.getContent());
		param.setTitle(dto.getTitle());
		param.setChannelId(dto.getChannelId());
		if(itIsBigUser()) {
			param.setQuickPass(true);
		}
		
		result = editArticleLong(userId, param, targetArticleId); 
		
		return result;
	}

	private CheckParamBeforeEditArticleResult checkParamBeforeEditArticle(Long userId, CreateArticleParam controllerParam) {
		CheckParamBeforeEditArticleResult result = new CheckParamBeforeEditArticleResult();

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
		result.setChannelId(channelId);
		
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
		result.setTitle(title);

		Long maxArticleLength = loadMaxArticleLength();
		String summaryStorePrefixPath = getArticleSummaryStorePrefixPath();
		String storePrefixPath = getArticleStorePrefixPath();
		if (StringUtils.isAnyBlank(summaryStorePrefixPath, storePrefixPath) || maxArticleLength < 1) {
			log.error("creating article serviceError  articleStorePrefixPath %s, maxArticleLength: %s, userId: %s", storePrefixPath, maxArticleLength, userId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		result.setStorePrefixPath(storePrefixPath);
		result.setSummaryStorePrefixPath(summaryStorePrefixPath);
		result.setIsSuccess();
		
		return result;
	}

	
	/**
	 * 
	 * 更新被编辑文档的信息
	 * @param targetArticleId
	 * @param backupArticleId
	 * @param editorId
	 * @param newFilePath
	 * @param po 
	 * @return
	 */
	private CommonResultCX updateEditedArticleLong(ArticleLong po, Long backupArticleId, Long editorId, String newFilePath) {
		CommonResultCX result = new CommonResultCX();
		if(po == null || editorId == null) {
			result.failWithMessage("参数缺失");
			return result;
		}
		
		Integer editCount = po.getEditCount();
		if(editCount == null) {
			editCount = 0;
		}
		
		po.setEditOf(backupArticleId);
		po.setEditCount(editCount + 1);
		po.setPath(newFilePath);
		po.setEditTime(LocalDateTime.now());
		po.setIsPass(false);
		
		int updateCount = articleLongMapper.updateByPrimaryKeySelective(po);
		if(updateCount < 1) {
			result.failWithMessage("更新数据异常");
			return result;
		}
		
		result.setIsSuccess();
		return result;
	}
}