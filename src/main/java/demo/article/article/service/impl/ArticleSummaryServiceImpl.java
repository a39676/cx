package demo.article.article.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleLongSummaryMapper;
import demo.article.article.pojo.bo.ArticleLongSummaryBO;
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
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.common.pojo.type.ResultTypeCX;
import demo.toyParts.vcode.pojo.param.GetVcodeByValueParam;
import demo.toyParts.vcode.pojo.po.VCode;
import demo.toyParts.vcode.service.VCodeService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
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
	public int insertArticleLongSummary(Long userId, Long articleId, String title, String finalFilePath) {
		String privateKey = encryptId(articleId);
		if(privateKey == null) {
			return 0;
		}
		ArticleLongSummary als = new ArticleLongSummary();
		als.setPrivateKey(privateKey);
		als.setUserId(userId);
		als.setArticleId(articleId);
		als.setArticleTitle(title);
		als.setFilePath(finalFilePath);
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
		FindArticleLongSummaryListMapperDTO mapperParam = new FindArticleLongSummaryListMapperDTO();
		
		if(StringUtils.isNotBlank(cp.getTitle())) {
			GetArticleChannelsResult getChannelResult = channelService.getArticleChannelsDynamic(request);
			List<Long> channelIdList = new ArrayList<Long>();
			for(ArticleChannelVO i : getChannelResult.getChannelList()) {
				if(numberUtil.matchInteger(i.getChannelId())) {
					channelIdList.add(Long.parseLong(i.getChannelId()));
				}
			}
			mapperParam.setChannelIdList(channelIdList);
		} else {
			mapperParam.addChannelId(cp.getArticleChannelId());
		}
		
		if(cp.getIsDelete() != null) {
			mapperParam.setIsDelete(cp.getIsDelete());
		}
		if(cp.getIsEdited() != null) {
			mapperParam.setIsEdited(cp.getIsEdited());
		}
		if(cp.getIsPass() != null) {
			mapperParam.setIsPass(cp.getIsPass());
		}
		if(cp.getIsReject() != null) {
			mapperParam.getIsReject();
		}
		if(cp.getEndTime() != null) {
			mapperParam.setEndTime(cp.getEndTime());
		}
		if(cp.getStartTime() != null) {
			mapperParam.setStartTime(cp.getStartTime());
		}
		if(cp.getTitle() != null) {
			mapperParam.setTitle(cp.getTitle());
		}
		if(cp.getLimit() != null ) {
			mapperParam.setLimit(mapperParam.getLimit());
		}
		if(mapperParam.getLimit() == null || mapperParam.getLimit() > articleConstantService.getMaxPageSize()) {
			mapperParam.setLimit(articleConstantService.getDefaultPageSize());
		}
		if(cp.getDesc() != null && !cp.getDesc()) {
			mapperParam.setDesc(false);
		}
		return mapperParam;
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
			if(StringUtils.isBlank(summaryBO.getFilePath()) || summaryBO.getCreateTime() == null || StringUtils.isBlank(summaryBO.getPrivateKey())) {
				continue;
			}
			tmpVO = new ArticleLongSummaryVO();
			strContent = ioUtil.getStringFromFile(summaryBO.getFilePath());
			voContentBuilder.append(strContent);
			List<String> lines = Arrays.asList(strContent.split("\n"));
			tmpVO.setArticleTitle(summaryBO.getArticleTitle());
			tmpVO.setFirstLine(lines.get(0));
			tmpVO.setCreateDateString(sdf.format(summaryBO.getCreateTime()));
			tmpVO.setCreateDateDescription(localDateTimeHandler.dateToStr(summaryBO.getCreateTime()));
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
			List<ArticleCommentCount> commentCountList,
			List<ArticleViewCount> viewCountList) {
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
		HashMap<Long, Long> commentCountMap = new HashMap<Long, Long>();
		if(commentCountList != null && commentCountList.size() > 0) {
			commentCountList.stream().forEach(po -> commentCountMap.put(po.getArticleId(), po.getCounting()));
		}
		HashMap<Long, Integer> viewCountMap = new HashMap<Long, Integer>();
		if(viewCountList != null && viewCountList.size() > 0) {
			viewCountList.stream().forEach(bo -> viewCountMap.put(bo.getArticleId(), bo.getViewCount()));
		}
		
		outputList = new ArrayList<ArticleLongSummaryVOV3>();
		for(ArticleLongSummaryBO summaryBO : summaryList) {
			if(StringUtils.isBlank(summaryBO.getFilePath()) || summaryBO.getCreateTime() == null || StringUtils.isBlank(summaryBO.getPrivateKey())) {
				continue;
			}
			tmpVO = new ArticleLongSummaryVOV3();
			try {
				strContent = ioUtil.getStringFromFile(summaryBO.getFilePath());
			} catch (Exception e) {
				strContent = "";
			}
			voContentBuilder.append(strContent);
			if(loadArticleHot) {
				tmpVO.setIsHot(loadArticleHot);
			}
			tmpVO.setArticleTitle(summaryBO.getArticleTitle());
			tmpVO.setCreateDateString(localDateTimeHandler.dateToStr(summaryBO.getCreateTime(), DateTimeUtilCommon.normalDateFormat));
			tmpVO.setCreateDateTimeString(localDateTimeHandler.dateToStr(summaryBO.getCreateTime(), DateTimeUtilCommon.normalDateTimeFormat));
			try {
				tmpVO.setPrivateKey(URLEncoder.encode(encryptId(summaryBO.getArticleId()), StandardCharsets.UTF_8.toString()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
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

		if (dto.getChannelIdList() == null || dto.getChannelIdList().isEmpty()) {
			return new ArrayList<ArticleLongSummaryBO>();
		}

		if ((dto.getLimit() != null && dto.getLimit() > articleConstantService.getDefaultPageSize()) || (dto.getLimit() == null)) {
			dto.setLimit(articleConstantService.getDefaultPageSize());
		}
		
		List<ArticleLongSummaryBO> boList = articleLongSummaryMapper.findArticleLongSummaryList(dto);
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
		findArticleLongSummaryListParamPreHandle(dto, request);
		
		if(dto.getEndTime() != null) {
			dto.setEndTime(dto.getEndTime().minusSeconds(1L));
		}
		
		/* 置限制只可浏览某时点之后的文章 */
		boolean isBigUser = isBigUser();
		if(!isBigUser) {
			Long maxReadingMonth = articleConstantService.getNormalUserMaxReadingMonth();;
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
		List<ArticleCommentCount> commentCountList = articleCommentController.findCommentCountByArticleId(articleIdList);
	
		List<ArticleViewCount> viewCountList = articleViewService.findArticleViewCountByArticleId(articleIdList);
		if(isBigUser && articleIdList.size() > 0) {
			articleHasCommentNotReviewIdList = articleCommentAdminController.findArticleIdWithCommentWaitingForReview(articleIdList);
		} else {
			articleHasCommentNotReviewIdList = new ArrayList<Long>();
		}
		
		List<ArticleLongSummaryVOV3> summaryVOList 
			= fillArticleSummaryContentV3(
//					dto.getIsHot(),
					false,
					summaryBOList, 
					articleHasCommentNotReviewIdList, 
					commentCountList,
					viewCountList);
		
		result.setArticleLongSummaryVOList(summaryVOList);
		
		return result;
	}
	
	private void findArticleLongSummaryListParamPreHandle(FindArticleLongSummaryListDTO controllerParam, HttpServletRequest request) {
		if(baseUtilCustom.isLoginUser()) {
			controllerParam.setUserId(baseUtilCustom.getUserId());
		}
		if(!baseUtilCustom.hasAdminRole()) {
			controllerParam.setIsPass(true);
			controllerParam.setIsDelete(false);
			controllerParam.setIsEdited(false);
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
		
		findArticleLongSummaryListParamPreHandle(controllerParam, request);

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
		List<ArticleCommentCount> commentCountList = articleCommentController.findCommentCountByArticleId(articleIdList);
		List<ArticleViewCount> viewCountList = articleViewService.findArticleViewCountByArticleId(articleIdList);
		if(baseUtilCustom.hasAdminRole() && articleIdList.size() > 0) {
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
	
	public FindArticleLongSummaryListResultV3 summaryListByChannelIdV5(FindArticleLongSummaryListDTO param, HttpServletRequest request) {
		FindArticleLongSummaryListResultV3 result = articleLongSummaryListByChannelIdV5(param, request);
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
	
	private FindArticleLongSummaryListResultV3 articleLongSummaryListByChannelIdV5(FindArticleLongSummaryListDTO dto, HttpServletRequest request) {
		/*
		 * 此处为非置顶部分
		 * 应该将置顶/非置顶文章分开处理
		 * 
		 * 有设置限制只可浏览某时点之后的文章
		 */
		FindArticleLongSummaryListResultV3 result = new FindArticleLongSummaryListResultV3();
		findArticleLongSummaryListParamPreHandle(dto, request);
		
		if(dto.getEndTime() != null) {
			dto.setEndTime(dto.getEndTime().minusSeconds(1L));
		}
		
		/* 置限制只可浏览某时点之后的文章 */
		boolean isBigUser = isBigUser();
		if(!isBigUser) {
			Long maxReadingMonth = articleConstantService.getNormalUserMaxReadingMonth();
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
		List<ArticleCommentCount> commentCountList = articleCommentController.findCommentCountByArticleId(articleIdList);
	
		List<ArticleViewCount> viewCountList = articleViewService.findArticleViewCountByArticleId(articleIdList);
		if(isBigUser && articleIdList.size() > 0) {
			articleHasCommentNotReviewIdList = articleCommentAdminController.findArticleIdWithCommentWaitingForReview(articleIdList);
		} else {
			articleHasCommentNotReviewIdList = new ArrayList<Long>();
		}
		
		List<ArticleLongSummaryVOV3> summaryVOList 
			= fillArticleSummaryContentV3(
//					dto.getIsHot(),
					false,
					summaryBOList, 
					articleHasCommentNotReviewIdList, 
					commentCountList,
					viewCountList);
		
		result.setArticleLongSummaryVOList(summaryVOList);
		
		return result;
	}
}
