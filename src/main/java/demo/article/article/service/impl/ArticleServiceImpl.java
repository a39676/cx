package demo.article.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.mapper.ArticleLongReviewMapper;
import demo.article.article.pojo.bo.UpdateEditedArticleLongBO;
import demo.article.article.pojo.constant.ArticleViewConstant;
import demo.article.article.pojo.dto.EditArticleLongDTO;
import demo.article.article.pojo.dto.FindArticleLongByConditionDTO;
import demo.article.article.pojo.dto.ReadyToEditArticleLongDTO;
import demo.article.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyDTO;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.po.ArticleLongReview;
import demo.article.article.pojo.result.CheckParamBeforeEditArticleResult;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.article.article.pojo.type.ArticleChannelType;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.pojo.vo.ArticleLongVO;
import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleChannelService;
import demo.article.article.service.ArticleService;
import demo.article.article.service.ArticleSummaryService;
import demo.article.article.service.ArticleViewService;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.user.controller.UsersController;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
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
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public ModelAndView buildCreatingArticleLongView(CreatingArticleParam controllerParam) {
		ModelAndView view = null;
		controllerParam.setUserId(baseUtilCustom.getUserId());
		if (controllerParam.getUserId() == null) {
			view = new ModelAndView(BaseViewConstant.viewError);
			view.addObject("exception", ResultTypeCX.notLoginUser.getName());
			return view;
		}

		view = new ModelAndView(ArticleViewConstant.creatingArticleLong);
		view.addObject("createNew", "true");
		return view;
	}

	/**
	 * 新建/编辑文章 if(编辑文章) { 另建一份备份po; 将现有数据复制过去; 并更新 isEdit = true; 将原对象 更新 editTime =
	 * now(), editCount + 1, editOf = (旧对象ID), editBy(编辑者ID), title = newTitle,
	 * filePath = newFilePath }
	 * 
	 * @throws IOException
	 * 
	 */
	private CommonResultCX editOrCreateArticleLong(Long editorId, CreateArticleParam controllerParam,
			Long editedArticleId) throws IOException {
		CommonResultCX result = new CommonResultCX();
		boolean editFlag = (editedArticleId != null && editorId != null);

		CheckParamBeforeEditArticleResult checkResult = checkParamBeforeEditArticle(editorId, controllerParam);
		if (!checkResult.isSuccess()) {
			result.failWithMessage(checkResult.getMessage());
			return result;
		}
		String newTitle = checkResult.getTitle();
		Long channelId = checkResult.getChannelId();

		String summaryStorePrefixPath = articleOptionService.getArticleSummaryStorePrefixPath();
		String storePrefixPath = articleOptionService.getArticleStorePrefixPath();
		Long maxArticleLength = articleOptionService.getMaxArticleLength();
		if (StringUtils.isAnyBlank(summaryStorePrefixPath, storePrefixPath) || maxArticleLength < 1) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		ArticleLong editedArticlePO = null;
		if (editFlag) {
			editedArticlePO = articleLongMapper.selectByPrimaryKey(editedArticleId);
			if (editedArticlePO == null || editedArticlePO.getIsEdited()) {
				result.failWithMessage("参数异常");
				return result;
			}
			boolean adminFlag = baseUtilCustom.hasAdminRole();
			if (!editedArticlePO.getUserId().equals(editorId) && !adminFlag) {
				result.failWithMessage("无权编辑");
				return result;
			}
		}

		ArticleFileSaveResult saveArticleResult = saveArticleFile(storePrefixPath, controllerParam.getContent());
		if (!saveArticleResult.isSuccess()) {
			result.failWithMessage(saveArticleResult.getMessage());
			return result;
		}

		String newFilePath = saveArticleResult.getFilePath();

		ArticleLong newArticle = new ArticleLong();
		Long newArticleId = snowFlake.getNextId();

		if (editFlag) {
			BeanUtils.copyProperties(editedArticlePO, newArticle);
			newArticle.setIsEdited(true);
			UpdateEditedArticleLongBO bo = new UpdateEditedArticleLongBO();
			bo.setBackupArticleId(newArticleId);
			bo.setChannelId(channelId);
			bo.setEditedArticlePO(editedArticlePO);
			bo.setEditorId(editorId);
			bo.setNewFilePath(newFilePath);
			bo.setTitle(newTitle);
			result = updateEditedArticleLong(bo);
			if (!result.isSuccess()) {
				return result;
			}
		} else {
			newArticle.setArticleTitle(newTitle);
			newArticle.setChannelId(channelId);
			newArticle.setUserId(editorId);
			newArticle.setFilePath(newFilePath);
		}
		newArticle.setArticleId(newArticleId);

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

		CommonResultCX saveArtieleSummaryResult = saveArticleSummaryFile(newArticleId, saveArticleResult.getFirstLine(),
				saveArticleResult.getImageUrls(), summaryStorePrefixPath);
		if (!saveArtieleSummaryResult.isSuccess()) {
			result.failWithMessage(saveArtieleSummaryResult.getMessage());
			return result;
		}

		insertCount = summaryService.insertArticleLongSummary(newArticle.getUserId(), newArticle.getArticleId(),
				saveArtieleSummaryResult.getMessage());
		if (insertCount < 1) {
			log.error("creating article insertArticleSummaryError %s, userId: %s", controllerParam.toString(),
					editorId);
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		if (isBigUser()) {
			if (editFlag) {
				quickPass(editedArticleId);
			} else {
				quickPass(newArticle.getArticleId());
			}
		}

		result.fillWithResult(ResultTypeCX.editArticleLongSuccess);
		return result;
	}

	@Override
	public CommonResult crateArticleLongPrefixServcie(CreateArticleParam cp)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException {
		CommonResult serviceResult = new CommonResult();

		if (!baseUtilCustom.isLoginUser()) {
			serviceResult.failWithMessage("请先登录");
			return serviceResult;
		}
		Long userId = baseUtilCustom.getUserId();

		serviceResult = editOrCreateArticleLong(userId, cp, null);
		return serviceResult;
	}

	private void quickPass(Long articleId) {
		String pk = URLEncoder.encode(systemOptionService.encryptId(articleId), StandardCharsets.UTF_8);
		if (pk != null) {
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

	/**
	 * 用于保存新建/编辑的摘要文档 在编辑情况下, userID 是编辑者ID, 不一定是原作者ID, 用于命名文件名
	 * 
	 * @param articleId
	 * @param title
	 * @param firstLine
	 * @param imageUrls
	 * @param summaryStorePrefixPath
	 * @return
	 */
	private CommonResultCX saveArticleSummaryFile(Long articleId, String firstLine, List<String> imageUrls,
			String summaryStorePrefixPath) {
		if (StringUtils.isBlank(firstLine)) {
			firstLine = "";
		}
		CommonResultCX result = new CommonResultCX();

		String fileName = snowFlake.getNextId() + ".txt";
		String timeFolder = LocalDate.now().toString();

		File mainFolder = new File(summaryStorePrefixPath + File.separator + timeFolder);
		String finalFilePath = summaryStorePrefixPath + File.separator + timeFolder + File.separator + fileName;

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

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.successWithMessage(finalFilePath);

		return result;
	}

	@Override
	public FindArticleLongResult findArticleLongByArticleSummaryPrivateKey(
			FindArticleLongByArticleSummaryPrivateKeyDTO param, HttpServletRequest request) {
		Long userId = baseUtilCustom.getUserId();

		FindArticleLongResult result = new FindArticleLongResult();
		ArticleLongVO articleVO = null;

		Long articleId = systemOptionService.decryptPrivateKey(param.getPrivateKey());
		if (articleId == null) {
			articleVO = new ArticleLongVO();
			articleVO.setContentLines(ResultTypeCX.errorParam.getName());
			result.setArticleLongVO(articleVO);
			return result;
		}
		result.setArticleId(articleId);

		articleViewService.insertOrUpdateViewCount(articleId);

		FindArticleLongByConditionDTO mapperDTO = new FindArticleLongByConditionDTO();
		BeanUtils.copyProperties(param, mapperDTO);
		mapperDTO.setArticleId(articleId);
		articleVO = articleLongMapper.findArticleLongByDecryptId(mapperDTO);

		if (articleVO == null) {
			result.fillWithResult(ResultTypeCX.errorWhenArticleLoad);
			articleVO = new ArticleLongVO();
			articleVO.setContentLines(ResultTypeCX.errorWhenArticleLoad.getName());
			result.setArticleLongVO(articleVO);
			return result;
		}

		if ((articleVO.getIsDelete() && !baseUtilCustom.hasAdminRole())
				|| !channelService.containThisChannel(request, articleVO.getChannelId())) {
			articleVO = new ArticleLongVO();
			articleVO.setContentLines("这篇文已经失踪了...请联系管理员...");
			result.setArticleLongVO(articleVO);
			return result;
		}

		fillArticleContent(articleVO, param.getPrivateKey(), userId);
		result.setArticleLongVO(articleVO);
		return result;
	}

	private void insertArticleVisitData(HttpServletRequest request, Long articleId) {
		visitDataService.insertVisitData(request, articleId.toString());
	}

	@Override
	public ModelAndView readArticleLong(String pk, HttpServletRequest request) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readArticleLongCleanBlog);
		FindArticleLongByArticleSummaryPrivateKeyDTO param = new FindArticleLongByArticleSummaryPrivateKeyDTO();
		param.setPrivateKey(pk);

		FindArticleLongResult result = findArticleLongByArticleSummaryPrivateKey(param, request);
		view.addObject("articleLongVO", result.getArticleLongVO());
		view.addObject("visitCount", visitDataService.getVisitCount());
		view.addObject("title", result.getArticleLongVO().getArticleTitle());
		view.addObject("donateImgUrl", articleOptionService.getDonateImgUrl());
		if (baseUtilCustom.isLoginUser()) {
			MyUserPrincipal user = baseUtilCustom.getUserPrincipal();
			view.addObject("nickName", user.getNickName());
			view.addObject("email", user.getEmail());
		}
		if (result.isSuccess()) {
			insertArticleVisitData(request, result.getArticleId());
		}

		return view;
	}

	private void fillArticleContent(ArticleLongVO articleLongVO, String pk, Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strContent = ioUtil.getStringFromFile(articleLongVO.getPath());
		articleLongVO.setContentLines(strContent);
		articleLongVO.setCreateDateString(sdf.format(articleLongVO.getCreateTime()));
		if (articleLongVO.getEditTime() != null) {
			articleLongVO.setEditDateString(sdf.format(articleLongVO.getEditTime()));
		}
		if (userId != null && userId.equals(articleLongVO.getUserId())) {
			articleLongVO.setIWroteThis(true);
		}

		if (articleLongVO.getUserId() != null) {
			articleLongVO.setHeadIamgeUrl(userController.findHeadImageUrl(articleLongVO.getUserId()));
		}
		try {
			articleLongVO.setPrivateKey(URLEncoder.encode(pk, StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean iWroteThis(String privateKey) {
		Long userId = baseUtilCustom.getUserId();
		if (userId == null || StringUtils.isBlank(privateKey)) {
			return false;
		}

		privateKey = URLDecoder.decode(privateKey, StandardCharsets.UTF_8);
		Long articleId = systemOptionService.decryptPrivateKey(privateKey);
		if (articleId == null) {
			return false;
		}

		if (articleLongMapper.iWroteThis(userId, articleId) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void refillArticleLongReviewCreatorId() {

		List<ArticleLongReview> reviewResultList = articleLongReviewMapper.findArticleCreatorIdIsNull();
		if (reviewResultList == null || reviewResultList.size() < 1) {
			return;
		}

		List<Long> articleIdList = reviewResultList.stream().map(ArticleLongReview::getArticleId)
				.collect(Collectors.toList());

		List<ArticleLong> articleList = articleLongMapper.findArticleLongList(articleIdList);
		if (articleList == null || articleList.size() < 1) {
			return;
		}

		Map<Long, Long> articleIdKeyCreatorId = articleList.stream()
				.collect(Collectors.toMap(ArticleLong::getArticleId, ArticleLong::getUserId));

		reviewResultList.stream().forEach(po -> po.setArticleCreatorId(articleIdKeyCreatorId.get(po.getArticleId())));

		articleLongReviewMapper.batchUpdateFillCreatorId(reviewResultList);
	}

	@Override
	public ModelAndView readyToEditArticleLong(ReadyToEditArticleLongDTO dto) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.creatingArticleLong);
		;

		ArticleLongVO vo = findArticleLongVOForEdit(dto);

		view.addObject("articleVO", vo);
		view.addObject("edit", "true");

		return view;
	}

	private ArticleLongVO findArticleLongVOForEdit(ReadyToEditArticleLongDTO dto) {
		ArticleLongVO vo;

		if (StringUtils.isBlank(dto.getPrivateKey())) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorParam.getName());
			return vo;
		}

		Long articleId = systemOptionService.decryptPrivateKey(dto.getPrivateKey());

		if (articleId == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorParam.getName());
			return vo;
		}

		FindArticleLongByConditionDTO mapperDTO = new FindArticleLongByConditionDTO();
		BeanUtils.copyProperties(dto, mapperDTO);
		mapperDTO.setArticleId(articleId);
		vo = articleLongMapper.findArticleLongByDecryptId(mapperDTO);

		if (vo == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultTypeCX.errorWhenArticleLoad.getName());
			return vo;
		}

		Long userId = baseUtilCustom.getUserId();
		boolean adminFlag = baseUtilCustom.hasAdminRole();
		if (vo.getUserId() != userId && !adminFlag) {
			vo = new ArticleLongVO();
			vo.setContentLines("没有编辑其他用户的文章的权限");
			return vo;
		}

		if (vo.getIsDelete() && !adminFlag) {
			vo = new ArticleLongVO();
			vo.setContentLines("这篇文已经失踪了...请联系管理员...");
			return vo;
		}

		fillArticleContent(vo, dto.getPrivateKey(), userId);

		return vo;
	}

	@Override
	public CommonResultCX editArticleLongHandler(EditArticleLongDTO dto)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException {

		CommonResultCX result = new CommonResultCX();
		if (StringUtils.isBlank(dto.getPk())) {
			result.failWithMessage("参数缺失");
			return result;
		}

		dto.setPk(URLDecoder.decode(dto.getPk(), StandardCharsets.UTF_8));
		Long targetArticleId = systemOptionService.decryptPrivateKey(dto.getPk());

		if (targetArticleId == null) {
			result.failWithMessage("参数错误");
			return result;
		}

		Long userId = baseUtilCustom.getUserId();

		CreateArticleParam param = new CreateArticleParam();
		param.setContent(dto.getContent());
		param.setTitle(dto.getTitle());
		param.setChannelId(dto.getChannelId());

		result = editOrCreateArticleLong(userId, param, targetArticleId);

		return result;
	}

	private CheckParamBeforeEditArticleResult checkParamBeforeEditArticle(Long userId,
			CreateArticleParam controllerParam) {
		CheckParamBeforeEditArticleResult result = new CheckParamBeforeEditArticleResult();

		String channelIdStr = controllerParam.getChannelId();
		if (StringUtils.isBlank(channelIdStr) || !numberUtil.matchInteger(channelIdStr)) {
			log.error("creating article errorParam " + controllerParam.toString() + ", userId: " + userId);
			result.setMessage("Error param");
			return result;
		}

		Long channelId = Long.parseLong(channelIdStr);

		ArticleChannels channel = channelService.findArticleChannelById(channelId);
		if (channel == null) {
			log.error("creating article checkPostLimitError %s, userId: %s", controllerParam.toString(), userId);
			result.setMessage("Error param");
			return result;
		} else {
			if (!ArticleChannelType.publicChannel.getCode().equals(channel.getChannelType())) {
				if (!channelService.canVisitThisChannel(userId, channelId)) {
					log.error("creating article checkPostLimitError %s, userId: %s", controllerParam.toString(), userId);
					result.setMessage("Error param");
					return result;
				}
			}
		}

		result.setChannelId(channelId);

		String title = null;
		if (isBigUser()) {
			title = controllerParam.getTitle();
		} else {
			title = sanitize(controllerParam.getTitle());
			controllerParam.setContent(sanitize(controllerParam.getContent()));
		}

		if (StringUtils.isBlank(title)) {
			result.failWithMessage("请输入标题");
			return result;
		}
		result.setTitle(title);

		result.setIsSuccess();

		return result;
	}

	/**
	 * 
	 * 更新被编辑文档的信息
	 * 
	 * @param targetArticleId
	 * @param backupArticleId
	 * @param editorId
	 * @param newFilePath
	 * @param po
	 * @return
	 */
	private CommonResultCX updateEditedArticleLong(UpdateEditedArticleLongBO bo) {
		CommonResultCX result = new CommonResultCX();
		if (bo.getEditedArticlePO() == null || bo.getEditorId() == null) {
			result.failWithMessage("参数缺失");
			return result;
		}

		ArticleLong po = bo.getEditedArticlePO();

		Integer editCount = po.getEditCount();
		if (editCount == null) {
			editCount = 0;
		}

		po.setArticleTitle(bo.getTitle());
		po.setEditOf(bo.getBackupArticleId());
		po.setEditCount(editCount + 1);
		po.setFilePath(bo.getNewFilePath());
		po.setEditTime(LocalDateTime.now());
		po.setChannelId(bo.getChannelId());
		po.setIsPass(false);

		int updateCount = articleLongMapper.updateByPrimaryKeySelective(po);
		if (updateCount < 1) {
			result.failWithMessage("更新数据异常");
			return result;
		}

		result.setIsSuccess();
		return result;
	}

}