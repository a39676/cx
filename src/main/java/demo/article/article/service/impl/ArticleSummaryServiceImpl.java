package demo.article.article.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleLongSummaryMapper;
import demo.article.article.pojo.bo.ArticleLongSummaryBO;
import demo.article.article.pojo.constant.ArticleConstant;
import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.dto.FindArticleLongSummaryListMapperDTO;
import demo.article.article.pojo.param.mapperParam.FindArticleHotSummaryListMapperParam;
import demo.article.article.pojo.po.ArticleLongSummary;
import demo.article.article.pojo.po.ArticleSummaryVCode;
import demo.article.article.pojo.po.ArticleViewCount;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResultV3;
import demo.article.article.pojo.type.ArticlePublicChannelType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.article.article.pojo.vo.ArticleLongSummaryVO;
import demo.article.article.pojo.vo.ArticleLongSummaryVOV3;
import demo.article.article.service.ArticleCatchVCodeService;
import demo.article.article.service.ArticleChannelService;
import demo.article.article.service.ArticleSummaryService;
import demo.article.article.service.ArticleViewService;
import demo.article.articleComment.controller.ArticleCommentAdminController;
import demo.article.articleComment.controller.ArticleCommentController;
import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.toyParts.vcode.pojo.param.GetVcodeByValueParam;
import demo.toyParts.vcode.pojo.po.VCode;
import demo.toyParts.vcode.service.VCodeService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleSummaryServiceImpl extends ArticleCommonService implements ArticleSummaryService {

	@Autowired
	private VCodeService vCodeService;
	@Autowired
	private ArticleCatchVCodeService articleCatchVCodeService;
	@Autowired
	private ArticleViewService articleViewService;
	@Autowired
	private ArticleLongSummaryMapper articleLongSummaryMapper;

	@Autowired
	private ArticleCommentAdminController articleCommentAdminController;
	@Autowired
	private ArticleCommentController articleCommentController;
	
	@Autowired
	private ArticleChannelService channelService;
	
	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		List<List<Character>> keys = getCustomKey();
		String privateKey = encryptArticleId(articleId, keys);
		if(privateKey == null) {
			return 0;
		}
		ArticleLongSummary als = new ArticleLongSummary();
		als.setPrivateKey(privateKey);
		als.setUserId(userId);
		als.setArticleId(articleId);
		als.setArticleTitle(title);
		als.setPath(finalFilePath);
		return articleLongSummaryMapper.insertSelective(als);
	}
	
	private List<ArticleLongSummaryBO> findArticleHotSummaryList(FindArticleHotSummaryListMapperParam param) {
		List<ArticleLongSummaryBO> boList;
		if(param.getChannelId() == null) {
			boList = new ArrayList<ArticleLongSummaryBO>();
			return boList;
		}
		
		boList = articleLongSummaryMapper.findArticleHotSummaryList(param);
		return boList;
	}
	
	private FindArticleLongSummaryListMapperDTO buildFindArticleLongSummaryListMapperDTO(FindArticleLongSummaryListDTO cp, HttpServletRequest request) {
		FindArticleLongSummaryListMapperDTO mp = new FindArticleLongSummaryListMapperDTO();
		
		if(StringUtils.isNotBlank(cp.getTitle())) {
			GetArticleChannelsResult getChannelResult = channelService.getArticleChannelsDynamic(request);
			List<Long> channelIdList = new ArrayList<Long>();
			for(ArticleChannelVO i : getChannelResult.getChannelList()) {
				if(numberUtil.matchInteger(i.getChannelId())) {
					channelIdList.add(Long.parseLong(i.getChannelId()));
				}
			}
			mp.setChannelIdList(channelIdList);
		} else {
			mp.addChannelId(cp.getArticleChannelId());
		}
		
		if(cp.getIsDelete() != null) {
			mp.setIsDelete(cp.getIsDelete());
		}
		if(cp.getIsEdited() != null) {
			mp.setIsEdited(cp.getIsEdited());
		}
		if(cp.getIsPass() != null) {
			mp.setIsPass(cp.getIsPass());
		}
		if(cp.getIsReject() != null) {
			mp.getIsReject();
		}
		if(cp.getEndTime() != null) {
			mp.setEndTime(cp.getEndTime());
		}
		if(cp.getStartTime() != null) {
			mp.setStartTime(cp.getStartTime());
		}
		if(cp.getTitle() != null) {
			mp.setTitle(cp.getTitle());
		}
		if(cp.getLimit() != null ) {
			mp.setLimit(mp.getLimit());
		}
		if(mp.getLimit() == null || mp.getLimit() > ArticleConstant.maxPageSize) {
			mp.setLimit(ArticleConstant.defaultPageSize);
		}
		if(cp.getDesc() != null && !cp.getDesc()) {
			mp.setDesc(false);
		}
		return mp;
	}

	/** 
	 * FIXME 2019-06-08 发现 需要处理
	 * 此方法在V3落实后考虑保留 因其中的评价参数模块 
	 * */
	protected List<ArticleLongSummaryVO> fillArticleSummaryContent(boolean loadArticleHot, List<ArticleLongSummaryBO> summaryList, Map<Long, ArticleEvaluationStatisticsVO> articleEvaluationStatisticsMap, List<Long> articleHasCommentNotReviewIdList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strContent = "";
		StringBuffer voContentBuilder = new StringBuffer();
		
		List<ArticleLongSummaryVO> outputList = null;
		if(summaryList == null) {
			outputList = new ArrayList<ArticleLongSummaryVO>();
		}
		ArticleLongSummaryVO tmpVO = null;
		if(!loadArticleHot && summaryList.size() < 1) {
			outputList = new ArrayList<ArticleLongSummaryVO>();
			tmpVO = new ArticleLongSummaryVO();
			tmpVO.setFirstLine("到底了...");
			tmpVO.setImgUrls(new ArrayList<String>());
			tmpVO.setCreateDateString("2000-01-01 00:00:00");
			tmpVO.setCreateDateDescription("");
			outputList.add(tmpVO);
			return outputList;
		}
		
		outputList = new ArrayList<ArticleLongSummaryVO>();
		for(ArticleLongSummaryBO summaryBO : summaryList) {
			if(StringUtils.isBlank(summaryBO.getPath()) || summaryBO.getCreateTime() == null || StringUtils.isBlank(summaryBO.getPrivateKey())) {
				continue;
			}
			tmpVO = new ArticleLongSummaryVO();
			strContent = ioUtil.getStringFromFile(summaryBO.getPath());
			voContentBuilder.append(strContent);
			List<String> lines = Arrays.asList(strContent.split("\n"));
			List<String> imgUrls = new ArrayList<String>();
			lines.stream().forEach(l -> {
				if(l.matches(imageHttpUrlPattern)) {
					imgUrls.add(l);
				}
			});
			tmpVO.setArticleTitle(summaryBO.getArticleTitle());
			tmpVO.setNickName(summaryBO.getNickName());
			tmpVO.setFirstLine(lines.get(0));
			tmpVO.setImgUrls(imgUrls);
			tmpVO.setCreateDateString(sdf.format(summaryBO.getCreateTime()));
			tmpVO.setCreateDateDescription(createDateDescription(summaryBO.getCreateTime()));
			tmpVO.setPrivateKey(summaryBO.getPrivateKey());
			tmpVO.setEvaluationMap(articleEvaluationStatisticsMap.get(summaryBO.getArticleId()).getEvaluationCodeAndCount());
			if(articleHasCommentNotReviewIdList != null && articleHasCommentNotReviewIdList.size() > 0 && articleHasCommentNotReviewIdList.contains(summaryBO.getArticleId())) {
				tmpVO.setHasCommentNotReview(true);
			}
			outputList.add(tmpVO);
		}
		return outputList;
	}
	
	private List<ArticleLongSummaryVOV3> fillArticleSummaryContentV3(
			boolean loadArticleHot, 
			List<ArticleLongSummaryBO> summaryList, 
			List<Long> articleHasCommentNotReviewIdList,
			List<ArticleCommentCountByArticleIdBO> commentCountList,
			List<ArticleViewCount> viewCountList) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strContent = "";
		StringBuffer voContentBuilder = new StringBuffer();
		
		List<ArticleLongSummaryVOV3> outputList = null;
		if(summaryList == null) {
			outputList = new ArrayList<ArticleLongSummaryVOV3>();
		}
		ArticleLongSummaryVOV3 tmpVO = null;
		if(!loadArticleHot && summaryList.size() < 1) {
			outputList = new ArrayList<ArticleLongSummaryVOV3>();
			return outputList;
		}
		HashMap<Long, Integer> commentCountMap = new HashMap<Long, Integer>();
		if(commentCountList != null && commentCountList.size() > 0) {
			commentCountList.stream().forEach(bo -> commentCountMap.put(bo.getArticleId(), bo.getCommentCount()));
		}
		HashMap<Long, Integer> viewCountMap = new HashMap<Long, Integer>();
		if(viewCountList != null && viewCountList.size() > 0) {
			viewCountList.stream().forEach(bo -> viewCountMap.put(bo.getArticleId(), bo.getViewCount()));
		}
		
		outputList = new ArrayList<ArticleLongSummaryVOV3>();
		for(ArticleLongSummaryBO summaryBO : summaryList) {
			if(StringUtils.isBlank(summaryBO.getPath()) || summaryBO.getCreateTime() == null || StringUtils.isBlank(summaryBO.getPrivateKey())) {
				continue;
			}
			tmpVO = new ArticleLongSummaryVOV3();
			strContent = ioUtil.getStringFromFile(summaryBO.getPath());
			voContentBuilder.append(strContent);
			List<String> lines = Arrays.asList(strContent.split("\n"));
			String imgUrl = "";
			for(int i = 0; i < lines.size() && imgUrl == null; i++) {
				if(lines.get(i).matches(imageHttpUrlPattern)) {
					imgUrl = lines.get(i);
				}
			}
			tmpVO.setArticleTitle(summaryBO.getArticleTitle());
			tmpVO.setNickName(summaryBO.getNickName());
			tmpVO.setImgUrl(imgUrl);
			tmpVO.setCreateDateString(sdfDate.format(summaryBO.getCreateTime()));
			tmpVO.setCreateDateTimeString(sdfDateTime.format(summaryBO.getCreateTime()));
			tmpVO.setPrivateKey(summaryBO.getPrivateKey());
			tmpVO.setHeadIamgeUrl(summaryBO.getHeadImage());
			if(commentCountMap.get(summaryBO.getArticleId()) != null) {
				tmpVO.setCommentCount(commentCountMap.get(summaryBO.getArticleId()));
			}
			if(viewCountMap.get(summaryBO.getArticleId()) != null) {
				tmpVO.setViewCount(viewCountMap.get(summaryBO.getArticleId()));
			}
			if(articleHasCommentNotReviewIdList != null && articleHasCommentNotReviewIdList.size() > 0 && articleHasCommentNotReviewIdList.contains(summaryBO.getArticleId())) {
				tmpVO.setHasCommentNotReview(true);
			}
			outputList.add(tmpVO);
		}
		return outputList;
	}

	private List<ArticleLongSummaryBO> findArticleLongSummaryList(FindArticleLongSummaryListMapperDTO dto) {

		if (dto.getStartTime() == null && dto.getEndTime() == null) {
			dto.setEndTime(LocalDateTime.now());
		}

		if (dto.getStartTime() != null && dto.getEndTime() != null
				&& (dto.getStartTime().isAfter(dto.getEndTime())
						|| dto.getStartTime().equals(dto.getEndTime()))) {
			return new ArrayList<ArticleLongSummaryBO>();
		}

		if (dto.getChannelIdList() == null || dto.getChannelIdList().size() < 1) {
			return new ArrayList<ArticleLongSummaryBO>();
		}

		if (dto.getLimit() != null && dto.getLimit() > ArticleConstant.maxPageSize) {
			dto.setLimit(ArticleConstant.defaultPageSize);
		}
		Long userId = dto.getUserId();
		dto.setUserId(null);
		
		List<ArticleLongSummaryBO> boList = articleLongSummaryMapper.findArticleLongSummaryList(dto);
		dto.setUserId(userId);
		return boList;
	}

	private FindArticleLongSummaryListResultV3 articleLongSummaryListByChannelIdV3(FindArticleLongSummaryListDTO dto, HttpServletRequest request) {
		/*
		 * 此处为非置顶部分
		 * 应该将置顶/非置顶文章分开处理
		 * 
		 * 有设置限制只可浏览某时点之后的文章
		 */
		FindArticleLongSummaryListResultV3 result = new FindArticleLongSummaryListResultV3();

		setFindArticleLongSummaryListParam(dto, request);
		
		if(dto.getEndTime() != null) {
			dto.setEndTime(dto.getEndTime().minusSeconds(1L));
		}
		
		/* 置限制只可浏览某时点之后的文章 */
		if(!dto.getHasAdminRole()) {
			String normalUserMaxReadingMonth = constantService.getValByName(SystemConstantStore.normalUserMaxReadingMonth);
			int maxReadingMonth = 1;
			if(numberUtil.matchInteger(normalUserMaxReadingMonth)) {
				maxReadingMonth = Integer.parseInt(normalUserMaxReadingMonth);
			}
			LocalDateTime earliestStartTime = LocalDateTime.now().minusMonths(maxReadingMonth);
			if(dto.getStartTime() == null || dto.getStartTime().isBefore(earliestStartTime)) {
				dto.setStartTime(earliestStartTime);
			}
		}
		
		Long channelId = dto.getArticleChannelId();
		if(channelId == null && StringUtils.isBlank(dto.getTitle())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		result.setChannelId(channelId);
		
		FindArticleLongSummaryListMapperDTO findSummaryBOListDTO = buildFindArticleLongSummaryListMapperDTO(dto, request);
		List<ArticleLongSummaryBO> summaryBOList = findArticleLongSummaryList(findSummaryBOListDTO);
		
		List<Long> articleIdList = new ArrayList<Long>();
		List<Long> articleHasCommentNotReviewIdList = null;
		summaryBOList.stream().forEach(bo -> articleIdList.add(bo.getArticleId()));
		List<ArticleCommentCountByArticleIdBO> commentCountList = articleCommentController.findCommentCountByArticleId(articleIdList);
	
		List<ArticleViewCount> viewCountList = articleViewService.findArticleViewCountByArticleId(articleIdList);
		if(dto.getHasAdminRole() && articleIdList.size() > 0) {
			articleHasCommentNotReviewIdList = articleCommentAdminController.findArticleIdWithCommentWaitingForReview(articleIdList);
		} else {
			articleHasCommentNotReviewIdList = new ArrayList<Long>();
		}
		
		List<ArticleLongSummaryVOV3> summaryVOList 
			= fillArticleSummaryContentV3(
					dto.getIsHot(), 
					summaryBOList, 
					articleHasCommentNotReviewIdList, 
					commentCountList,
					viewCountList);
		
		result.setArticleLongSummaryVOList(summaryVOList);
		
		return result;
	}
	
	private void setFindArticleLongSummaryListParam(FindArticleLongSummaryListDTO controllerParam, HttpServletRequest request) {
		if(baseUtilCustom.isLoginUser()) {
			controllerParam.setUserId(baseUtilCustom.getUserId());
		}
		if(!baseUtilCustom.hasAdminRole()) {
			controllerParam.setIsPass(true);
			controllerParam.setIsDelete(false);
			controllerParam.setIsEdited(false);
		} else {
			controllerParam.setHasAdminRole(true);
		}
		
		if(findHostNameFromRequst(request).contains("site")) {
			if(StringUtils.isBlank(controllerParam.getVcode())) {
				controllerParam.setVcode("defaultVcode");
			}
		} else {
			controllerParam.setVcode(null);
		}
	}
	
	private FindArticleLongSummaryListResultV3 articleLongSummaryHotListByChannelIdV3(FindArticleLongSummaryListDTO controllerParam, HttpServletRequest request) {
		/*
		 * 此处为置顶部分
		 * 应该将置顶/非置顶文章分开处理 
		 * 部分情况下 需要处理 vcode 动态插入部分 "置顶"文章
		 * 不设创建时间限制(避免与置顶时间冲突----------> 保证置顶生效期间效果)
		 */
		
		FindArticleLongSummaryListResultV3 result = new FindArticleLongSummaryListResultV3();
		
		setFindArticleLongSummaryListParam(controllerParam, request);

		if(controllerParam.getEndTime() == null) {
			controllerParam.setEndTime(LocalDateTime.now());
		}
		if(controllerParam.getStartTime() == null) {
			controllerParam.setStartTime(LocalDateTime.MIN);
		}
		
		
		Long channelId = controllerParam.getArticleChannelId();
		visitDataService.insertVisitData(request, result.getChannelId().toString());
		if(channelId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		result.setChannelId(channelId);
		
		FindArticleHotSummaryListMapperParam findHotSummaryBOListParam = new FindArticleHotSummaryListMapperParam();
		findHotSummaryBOListParam.setChannelId(channelId);
		List<ArticleLongSummaryBO> hotSummaryBOList = findArticleHotSummaryList(findHotSummaryBOListParam);
		
//		如有vcode的处理逻辑
		if(ArticlePublicChannelType.c1.equals(ArticlePublicChannelType.getType(channelId)) && StringUtils.isNotBlank(controllerParam.getVcode())) {
			GetVcodeByValueParam getVCodeParam = new GetVcodeByValueParam();
			getVCodeParam.setCodeValue(controllerParam.getVcode());
			VCode vcode = vCodeService.findVCode(getVCodeParam);
			vCodeService.updateUseCount(vcode);
			ArticleSummaryVCode targetArticleSummaryInfo = articleCatchVCodeService.findArticleSummaryInfo(vcode);
			if(targetArticleSummaryInfo != null) {
				ArticleLongSummaryBO targetSummaryBO = articleLongSummaryMapper.findArticleLongSummary(targetArticleSummaryInfo.getArticleId());
 				hotSummaryBOList.add(0, targetSummaryBO);
			}
		}
		
		List<Long> articleIdList = new ArrayList<Long>();
		List<Long> articleHasCommentNotReviewIdList = null;
		hotSummaryBOList.stream().forEach(bo -> articleIdList.add(bo.getArticleId()));
		List<ArticleCommentCountByArticleIdBO> commentCountList = articleCommentController.findCommentCountByArticleId(articleIdList);
		List<ArticleViewCount> viewCountList = articleViewService.findArticleViewCountByArticleId(articleIdList);
		if(controllerParam.getHasAdminRole() && articleIdList.size() > 0) {
			articleHasCommentNotReviewIdList = articleCommentAdminController.findArticleIdWithCommentWaitingForReview(articleIdList);
		} else {
			articleHasCommentNotReviewIdList = new ArrayList<Long>();
		}
		
		List<ArticleLongSummaryVOV3> hotSummaryVOList 
			= fillArticleSummaryContentV3(
					controllerParam.getIsHot(), 
					hotSummaryBOList, 
					articleHasCommentNotReviewIdList, 
					commentCountList,
					viewCountList);
		
		result.setArticleLongSummaryVOList(hotSummaryVOList);
		result.setIsSuccess();
		
		return result;
	}
	
	@Override
	public FindArticleLongSummaryListResultV3 summaryListByChannelIdV4(FindArticleLongSummaryListDTO param, HttpServletRequest request) {
		FindArticleLongSummaryListResultV3 result = articleLongSummaryListByChannelIdV3(param, request);
		if(param.getIsHot()) {
			FindArticleLongSummaryListResultV3 hotResult = articleLongSummaryHotListByChannelIdV3(param, request);
			List<ArticleLongSummaryVOV3> tmpVOList = hotResult.getArticleLongSummaryVOList();
			if(tmpVOList != null && tmpVOList.size() > 0) {
				tmpVOList.addAll(result.getArticleLongSummaryVOList());
				result.setArticleLongSummaryVOList(tmpVOList);
			}
		}
		return result;
	}
}
