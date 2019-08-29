package demo.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import dateTimeHandle.DateUtilCustom;
import demo.article.mapper.ArticleLongComplaintMapper;
import demo.article.mapper.ArticleLongMapper;
import demo.article.mapper.ArticleLongReviewMapper;
import demo.article.mapper.ArticleUserDetailMapper;
import demo.article.pojo.bo.ArticleUUIDChannelStoreBO;
import demo.article.pojo.constant.ArticleConstant;
import demo.article.pojo.constant.ArticleViewConstant;
import demo.article.pojo.param.controllerParam.ArticleLongComplaintParam;
import demo.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyParam;
import demo.article.pojo.param.controllerParam.LikeHateThisChannelParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.param.mapperParam.FindArticleLongParam;
import demo.article.pojo.param.mapperParam.FindArticleUserDetailByUserIdChannelIdParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserDetailParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserPostLimitParam;
import demo.article.pojo.po.ArticleChannels;
import demo.article.pojo.po.ArticleLong;
import demo.article.pojo.po.ArticleLongComplaint;
import demo.article.pojo.po.ArticleLongReview;
import demo.article.pojo.po.ArticleUserDetail;
import demo.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.article.pojo.type.ArticleChannelLikeOrHateType;
import demo.article.pojo.type.ArticleChannelType;
import demo.article.pojo.type.ArticleReviewType;
import demo.article.pojo.vo.ArticleLongVO;
import demo.article.service.ArticleAdminService;
import demo.article.service.ArticleChannelService;
import demo.article.service.ArticleService;
import demo.article.service.ArticleSummaryService;
import demo.article.service.ArticleViewService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.controller.UsersController;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.image.controller.ImageController;
import demo.util.BaseUtilCustom;
import ioHandle.FileUtilCustom;
import net.sf.json.JSONObject;

@Service
public class ArticleServiceImpl extends ArticleCommonService implements ArticleService {

	@Autowired
	private BaseUtilCustom baseUtilCustom;
	@Autowired
	private ImageController imageController;
	@Autowired
	private UsersController userController;
	
	@Autowired
	private SystemConstantService systemConstantService;
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
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	@Autowired
	private ArticleLongComplaintMapper articleLongComplaintMapper;
	
	private static String articleStorePrefixPath = "";
	private static String articleSummaryStorePrefixPath = "";
	private static Long maxArticleLength = 0L;
	
	
	static {{
	}}
	
	private boolean loadArticleStorePath() {
		articleStorePrefixPath = systemConstantService.getValByName(SystemConstantStore.articleStorePrefixPath);
		articleSummaryStorePrefixPath = systemConstantService
				.getValByName(SystemConstantStore.articleSummaryStorePrefixPath);
		if (articleStorePrefixPath.length() > 0 && articleSummaryStorePrefixPath.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean loadMaxArticleLength() {
		maxArticleLength = Long.parseLong(systemConstantService.getValByName(SystemConstantStore.maxArticleLength));
		if (maxArticleLength > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getImageHttpUrlPattern() {
		return imageHttpUrlPattern;
	}

	/**
	 * 将输入的 line 的所有 图片url 提取到 imageUrls,
	 * 并将 line 中的 图片url 前后都加上"\n"换行符
	 * @param line
	 * @param imageUrls
	 * @return
	 */
	private String imageUrlHandle(String line, List<String> imageUrls) {
		if (StringUtils.isBlank(line)) {
			return line;
		}

		List<String> urls = new ArrayList<String>();
		Pattern imageUrlPattern = Pattern.compile(imageHttpUrlPattern);
		Matcher matcher;

		String[] parts = line.split("http");
		for (String p : parts) {
			p = "http" + p;
			matcher = imageUrlPattern.matcher(p);
			if (matcher.find() && !urls.contains(matcher.group(1))) {
				urls.add(matcher.group(1));
			}
		}

		if (urls.size() < 1) {
			return line;
		}

		imageUrls.addAll(urls);

		for (String url : urls) {
			line = line.replaceAll(url, "\n" + url + "\n");
		}

		return line;
	}

	@Override
	public Long decryptPrivateKey(String pk) {
		return decryptArticlePrivateKey(pk);
	}
	
	@Override
	public String encryptId(Long id) {
		return encryptArticleId(id, getCustomKey());
	}
	
	private CommonResult batchCreateArticleLong(Long userId, CreateArticleParam controllerParam) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		CommonResult result = new CommonResult();
		String superAdminKey = systemConstantService.getValByName(SystemConstantStore.superAdminKey);
		if(StringUtils.isBlank(superAdminKey) 
				|| StringUtils.isBlank(controllerParam.getSuperAdminKey()) 
				|| !superAdminKey.equals(controllerParam.getSuperAdminKey())) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		String oldContent = controllerParam.getContent();
		List<String> lines = Arrays.asList(oldContent.split("http"));
		if(lines.size() < 1) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		String title = controllerParam.getTitle();
		String uuid = controllerParam.getUuid();
		
		JSONObject tmpJson = null;
		CommonResult tmpResult = null;
		int successCount = 0;
		for(String line : lines) {
			if(!StringUtils.isBlank(line)) {
				tmpJson = new JSONObject();
				tmpJson.put("title", title);
				tmpJson.put("uuid", uuid);
				tmpJson.put("content", "http" + line);
				tmpResult = createArticleLong(userId, controllerParam);
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
	public ModelAndView creatingArticleLong(CreatingArticleParam controllerParam) {
		ModelAndView view = null;
		if(controllerParam.getUserId() == null) {
			view = new ModelAndView(BaseViewConstant.viewError);
			view.addObject("exception", ResultType.notLoginUser.getName());
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
		
		return view;
	}
	
	private CommonResult createArticleLong(Long userId, CreateArticleParam controllerParam) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		CommonResult result = new CommonResult();
		int insertCount = 0;
		
		String uuid = controllerParam.getUuid();
		if(StringUtils.isBlank(uuid)) {
			log.error("creating article errorParam %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		ArticleUUIDChannelStoreBO channelUUIDStore = channelService.getArticleUUIDChannelStore();
		if(channelUUIDStore == null) {
			log.error("creating article channelUUIDError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultType.channelUUIDError);
			return result;
		}
		
		Long channelId = channelUUIDStore.getChannelId(uuid);
		if(channelId == null) {
			log.error("creating article channelUUIDError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultType.channelUUIDError);
			return result;
		}
		
		Integer postLimit = articleUserDetailMapper.findArticleUserPostLimit(userId, channelId);
		if(postLimit == null) {
			ArticleChannels channel = channelService.findArticleChannelById(channelId);
			if(channel == null || !ArticleChannelType.publicChannel.getCode().equals(channel.getChannelType())) {
				log.error("creating article checkPostLimitError %s, userId: %s", controllerParam.toString(), userId);
				result.fillWithResult(ResultType.errorParam);
				return result;
			}
			
			FindArticleUserDetailByUserIdChannelIdParam findUserChannelParam = new FindArticleUserDetailByUserIdChannelIdParam();
			findUserChannelParam.setUserId(userId);
			findUserChannelParam.setChannelId(channelId);
			ArticleUserDetail userChannel = articleUserDetailMapper.findArticleUserDetailByUserIdChannelId(findUserChannelParam);
			if(userChannel == null) {
				ArticleUserDetail newArticleUserDetail = new ArticleUserDetail();
				newArticleUserDetail.setUserId(userId);
				newArticleUserDetail.setArticleChannelId(channelId);
				newArticleUserDetail.setDailyChannelPostLimit(ArticleConstant.firstVisitDailyPostLimit);
				insertCount = articleUserDetailMapper.insertSelective(newArticleUserDetail);
				
				if(insertCount < 1) {
					log.error("creating article insertCountError %s, userId: %s", controllerParam.toString(), userId);
					result.fillWithResult(ResultType.errorParam);
					return result;
				} else {
					postLimit = ArticleConstant.firstVisitDailyPostLimit;
				}
			}
		}
		if(postLimit < 1) {
			result.fillWithResult(ResultType.articleChannelPostLimit);
			return result;
		}
		
		String title = StringEscapeUtils.escapeHtml(String.valueOf(controllerParam.getTitle()));

		if (articleStorePrefixPath.length() < 1 || maxArticleLength < 1) {
			if (!loadArticleStorePath() || !loadMaxArticleLength()) {
				log.error("creating article serviceError  articleStorePrefixPath %s, maxArticleLength: %s, userId: %s", articleStorePrefixPath, maxArticleLength, userId);
				result.fillWithResult(ResultType.serviceError);
				return result;
			}
		}

		ArticleLong newArticle = new ArticleLong();

		ArticleFileSaveResult saveArticleResult = saveArticleFile(articleStorePrefixPath, userId, controllerParam.getContent());
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
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
		if (saveArticleResult.getImageUrls().size() > 0) {
			imageController.insertImageFromArticle(saveArticleResult.getImageUrls(), newArticle.getArticleId());
		}

		saveArticleResult.setArticleId(newArticleId);

		CommonResult saveArtieleSummaryResult = saveArticleSummaryFile(userId, newArticle.getArticleId(), title,
				saveArticleResult.getFirstLine(), saveArticleResult.getImageUrls());
		if (!saveArtieleSummaryResult.isSuccess()) {
			return saveArtieleSummaryResult;
		}

		insertCount = summaryService.insertArticleLongSummary(userId, newArticle.getArticleId(), title,
				saveArtieleSummaryResult.getMessage());
		if (insertCount < 1) {
			log.error("creating article insertArticleSummaryError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
		UpdateArticleUserPostLimitParam updateArticleUserPostLimitParam = new UpdateArticleUserPostLimitParam();
		updateArticleUserPostLimitParam.setChannelId(channelId);
		updateArticleUserPostLimitParam.setUserId(userId);
		updateArticleUserPostLimitParam.setNewPostLimit(postLimit - 1);
		insertCount = articleUserDetailMapper.updateArticleUserPostLimit(updateArticleUserPostLimitParam);
		if (insertCount < 1) {
			log.error("creating article updateArticleUserPostLimitError %s, userId: %s", controllerParam.toString(), userId);
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
		if(controllerParam.getQuickPass()) {
			quickPass(newArticle.getArticleId());
		}
		
		result.fillWithResult(ResultType.createArticleLongSuccess);
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
		if(baseUtilCustom.hasAnyRole(
				RolesType.ROLE_POSTER.getName(),
				RolesType.ROLE_ADMIN.getName(),
				RolesType.ROLE_SUPER_ADMIN.getName())) {
			cp.setQuickPass(true);
		} else {
			cp.setQuickPass(false);
		}
		
		if(StringUtils.isNotBlank(cp.getSuperAdminKey())) {
			serviceResult = batchCreateArticleLong(userId, cp);
		} else {
			serviceResult = createArticleLong(userId, cp);
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
				result.fillWithResult(ResultType.serviceError);
				return result;
			}
		}

		if(maxArticleLength <= 0) {
			loadMaxArticleLength();
		}
		
		if (content.length() > maxArticleLength) {
			result.fillWithResult(ResultType.articleTooLong);
			return result;
		}

		FileUtilCustom iou = new FileUtilCustom();
		if (StringUtils.isBlank(content) || content.replaceAll("\\s", "").length() < 6) {
			result.fillWithResult(ResultType.articleTooShort);
			return result;
		}

		List<String> lines = Arrays.asList(content.split(System.lineSeparator()));
		List<String> imageUrls = new ArrayList<String>();
		List<String> escapeLines = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();

		for (String line : lines) {
			escapeLines.add(StringEscapeUtils.escapeHtml(line));
		}

		for (String line : escapeLines) {
			if(StringUtils.isNotBlank(line)) {
				sb.append(imageUrlHandle(line, imageUrls) + System.lineSeparator());
			}
		}
		
		String articleContentAfterTrim = sb.toString().trim();

		try {
			iou.byteToFile(articleContentAfterTrim.getBytes("utf8"), finalFilePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.fillWithResult(ResultType.errorWhenArticleSave);
			return result;
		}

		result.setFilePath(finalFilePath);
		result.setImageUrls(imageUrls);
		lines = Arrays.asList(articleContentAfterTrim.split("\n"));
		for(int i = 0; i < lines.size(); i++) {
			if(!lines.get(i).matches(imageHttpUrlPattern)) {
				result.setFirstLine(lines.get(i));
				i = lines.size();
			}
		}

		result.setIsSuccess();
		return result;
	}

	private CommonResult saveArticleSummaryFile(Long userId, Long articleId, String title, String firstLine,
			List<String> imageUrls) {
		if(StringUtils.isBlank(firstLine)) {
			firstLine = "";
		}
		CommonResult result = new CommonResult();
		if(!loadCustomKey()) {
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
		String fileName = userId + "L" + UUID.randomUUID().toString().substring(0, 8) + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(articleSummaryStorePrefixPath + timeFolder);
		String finalFilePath = articleSummaryStorePrefixPath + timeFolder + "/" + fileName;

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.fillWithResult(ResultType.serviceError);
				return result;
			}
		}

		FileUtilCustom iou = new FileUtilCustom();

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
			iou.byteToFile(sb.toString().getBytes("utf8"), finalFilePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.fillWithResult(ResultType.errorWhenArticleSave);
			return result;
		}

		result.successWithMessage(finalFilePath);

		return result;
	}

	@Override
	public FindArticleLongResult findArticleLongByArticleSummaryPrivateKey(FindArticleLongByArticleSummaryPrivateKeyParam param) {
		FindArticleLongResult result = new FindArticleLongResult();
		ArticleLongVO vo = null;
		
		Long articleId = decryptArticlePrivateKey(param.getPrivateKey());
		if(articleId == null) {
			vo = new ArticleLongVO();
			vo.setContentLines(ResultType.errorParam.getName());
			result.setArticleLongVO(vo);
			return result;
		}
		result.setArticleId(articleId);
		param.setArticleId(articleId);
		
		articleViewService.insertOrUpdateViewCount(articleId);
		
		vo = articleLongMapper.findArticleLongByDecryptId(param);
		if(vo == null) {
			result.fillWithResult(ResultType.errorWhenArticleLoad);
			vo = new ArticleLongVO();
			vo.setContentLines(ResultType.errorWhenArticleLoad.getName());
			result.setArticleLongVO(vo);
			return result;
		}
		fillArticleContent(vo, param.getPrivateKey(), param.getUserId());
		result.setArticleLongVO(vo);
		return result;
	}

	private void fillArticleContent(ArticleLongVO articleLongVO, String pk, Long userId) {
		FileUtilCustom iou = new FileUtilCustom();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strContent = iou.getStringFromFile(articleLongVO.getPath());
		List<String> strLines = Arrays.asList(strContent.split("\n"));
		StringBuffer outputLines = new StringBuffer();
		String line = "";
		for(int i = 0; i < strLines.size(); i++) {
			line = strLines.get(i);
			if(line.matches(imageHttpUrlPattern)) {
				line = "<a target=\"_blank\" href=\"" + line + "\">想看原图</a><br>"
						+ "<img name=\"articleImage\" pk=\""+ pk +"\" fold=\"1\" src=\"" + line + "\" style=\"width: 100px; height: 100px;\">"
						+ "<br>";
			}
			outputLines.append(line + "<br>");
		}
		articleLongVO.setContentLines(outputLines.toString());
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
	public boolean iWroteThis(Long userId, String privateKey) {
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
	public CommonResult likeOrHateThisChannel(LikeHateThisChannelParam inputParam) {
		CommonResult result = new CommonResult();
		if(inputParam.getUserId() == null 
				|| inputParam.getLikeOrHate() == null 
				|| (inputParam.getLikeOrHate() != 1 && inputParam.getLikeOrHate() != 0 && inputParam.getLikeOrHate() != -1)
				|| StringUtils.isBlank(inputParam.getUuid())) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		ArticleUUIDChannelStoreBO channelUUIDStore = channelService.getArticleUUIDChannelStore();
		if(channelUUIDStore == null) {
			result.fillWithResult(ResultType.channelUUIDError);
			return result;
		}
		
		Long channelId = channelUUIDStore.getChannelId(inputParam.getUuid());
		if(channelId == null) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		FindArticleUserDetailByUserIdChannelIdParam findChannelParam = new FindArticleUserDetailByUserIdChannelIdParam();
		findChannelParam.setUserId(inputParam.getUserId());
		findChannelParam.setChannelId(channelId);
		ArticleUserDetail articleUserDetail = articleUserDetailMapper.findArticleUserDetailByUserIdChannelId(findChannelParam);
		if(articleUserDetail == null || !articleUserDetail.getIsFlash()) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		ArticleChannelLikeOrHateType likeOrHateType = getArticleChannelLikeOrHateType(articleUserDetail);
		
		UpdateArticleUserDetailParam param = new UpdateArticleUserDetailParam();
		param.setUserId(inputParam.getUserId());
		param.setArticleChannelId(channelId);
		param.setCoefficient(likeOrHateType.getCode() + (inputParam.getLikeOrHate() * 10));
		if(inputParam.getLikeOrHate() == 1) {
			param.setLikeCount(1);
		} else if(inputParam.getLikeOrHate() == -1){
			param.setIsFlash(false);
			param.setHateCount(1);
			param.setIsFlashUpdateTime(DateUtilCustom.dateDiffDays(likeOrHateType.getDelayDays()));
		}
		
		articleUserDetailMapper.updateArticleUserDetail(param);
		
		result.fillWithResult(ResultType.success);
		return result;
	}
	
	private ArticleChannelLikeOrHateType getArticleChannelLikeOrHateType(ArticleUserDetail articleUserDetail) {
		if(articleUserDetail.getHateChannelCount() == null) {
			articleUserDetail.setHateChannelCount(0);
		}
		if(articleUserDetail.getLikeChannelCount() == null) {
			articleUserDetail.setLikeChannelCount(0);
		}
		int likeOrHateLevel = articleUserDetail.getHateChannelCount() + articleUserDetail.getLikeChannelCount();
		return ArticleChannelLikeOrHateType.getType(likeOrHateLevel);
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
	public CommonResult articleLongComplaint(ArticleLongComplaintParam controllerParam) {
		CommonResult result = new CommonResult();
		
		if (StringUtils.isBlank(controllerParam.getPk()) || StringUtils.isBlank(controllerParam.getComplaintReason())) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		String complaintReason = StringEscapeUtils.escapeHtml(controllerParam.getComplaintReason());
		if(StringUtils.isBlank(complaintReason)) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		} else if (complaintReason.length() > 512) {
			result.fillWithResult(ResultType.articleTooLong);
			return result;
		}
		

			
		Long articleId = decryptArticlePrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		FindArticleLongParam findArticleLongParam = new FindArticleLongParam();
		findArticleLongParam.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(findArticleLongParam);
		if(article == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		
		ArticleLongComplaint complaint = new ArticleLongComplaint();
		
		complaint.setCreateTime(new Date());
		complaint.setComplaintUserId(controllerParam.getComplaintUserId());
		complaint.setArticleId(articleId);
		complaint.setArticleCreatorId(article.getUserId());
		complaint.setArticleTitle(article.getArticleTitle());
		complaint.setComplaintReason(complaintReason);

		articleLongComplaintMapper.insert(complaint);
		
		result.fillWithResult(ResultType.complaintReciveSuccess);
		return result;
	}
}