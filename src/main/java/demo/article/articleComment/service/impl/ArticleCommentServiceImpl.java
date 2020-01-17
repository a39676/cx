package demo.article.articleComment.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.vo.ArticleCommentVO;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.article.article.service.ArticleEvaluationService;
import demo.article.article.service.ArticleService;
import demo.article.article.service.impl.ArticleCommonService;
import demo.article.articleComment.mapper.ArticleCommentMapper;
import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.article.articleComment.pojo.bo.FindCommentByArticleIdBO;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.dto.mapperParam.FindCommentByArticleIdParam;
import demo.article.articleComment.pojo.dto.mapperParam.JustCommentParam;
import demo.article.articleComment.pojo.po.ArticleComment;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.article.articleComment.service.ArticleCommentService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleCommentServiceImpl extends ArticleCommonService implements ArticleCommentService {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleEvaluationService articleEvaluationService;
	@Autowired
	private ArticleCommentAdminService articleCommentAdminService;
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	
	@Autowired
	private FileUtilCustom ioUtil;
	
	private String articleCommentStorePrefixPath;
	
	private boolean loadArticleCommontStorePath() {
		articleCommentStorePrefixPath = constantService.getValByName(SystemConstantStore.articleCommentStorePrefixPath);
		if (articleCommentStorePrefixPath.length() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private Long loadMaxArticleLength() {
		String maxCommentLengthStr = constantService.getValByName(SystemConstantStore.maxArticleLength);
		Long maxCommentLength = null;
		try {
			maxCommentLength = Long.parseLong(maxCommentLengthStr);
		} catch (Exception e) {
			maxCommentLength = 0L;
		}
		return maxCommentLength;
	}
	
	@Override
	public CommonResultCX creatingArticleComment(Long userId, CreateArticleCommentDTO inputParam) throws IOException {
		CommonResultCX result = new CommonResultCX();
		if(!loadArticleCommontStorePath() || loadMaxArticleLength() <= 0) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		if(userId == null || StringUtils.isBlank(inputParam.getContent())) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		if(inputParam.getContent().replaceAll("\\s", "").length() < 6) {
			result.fillWithResult(ResultTypeCX.articleTooShort);
			return result;
		}
		Long articleId = articleService.decryptPrivateKey(inputParam.getPk());
		
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		JustCommentParam justCommentParam = new JustCommentParam();
		justCommentParam.setUserId(userId);
		justCommentParam.setStartTime(new Date(System.currentTimeMillis() - 1000L * 60));
		int i = articleCommentMapper.justComment(justCommentParam);
		if(i > 0) {
			result.fillWithResult(ResultTypeCX.justComment);
			return result;
		}
		
		boolean bigUserFlag = isBigUser();
		if(!bigUserFlag) {
			PolicyFactory filter = textFilter.getFilter();
			inputParam.setContent(filter.sanitize(inputParam.getContent()));
		} 

		ArticleFileSaveResult saveFileResult = saveArticleCommentFile(articleCommentStorePrefixPath, userId, inputParam.getContent());
		if(!saveFileResult.isSuccess()) {
			return saveFileResult;
		}
		
		ArticleComment newComment = new ArticleComment();
		Long newCommonId = snowFlake.getNextId();
		newComment.setArticleCommentId(newCommonId);
		newComment.setArticleId(articleId);
		newComment.setPath(saveFileResult.getFilePath());
		newComment.setUserId(userId);
		articleCommentMapper.insertSelective(newComment);
		
		if(bigUserFlag) {
			PassArticleCommentDTO param = new PassArticleCommentDTO();
			param.setCommentId(newCommonId);
			articleCommentAdminService.passArticleComment(param );
		}
		
		result.setIsSuccess();
		
		return result;
	}
	
	private ArticleFileSaveResult saveArticleCommentFile(String storePrefixPath, Long userId, String content) throws IOException {
		return articleService.saveArticleFile(storePrefixPath, userId, content);
	}
	
	@Override
	public FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageDTO controllerParam) {
//		TODO
		FindArticleCommentPageResult result = new FindArticleCommentPageResult();
		List<ArticleCommentVO> commentVOList = new ArrayList<ArticleCommentVO>();
		
		if(StringUtils.isBlank(controllerParam.getPk())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(controllerParam.getStartTime() != null && controllerParam.getStartTime().getTime() < theStartTime) {
			controllerParam.setStartTime(new Date(theStartTime));
		}
		if(controllerParam.getStartTime() != null) {
			controllerParam.setStartTime(new Date(controllerParam.getStartTime().getTime() + 1));
		}
		
		Long articleId = articleService.decryptPrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		result.setPk(controllerParam.getPk());
		
		FindCommentByArticleIdParam findCommentByArticleIdParam = new FindCommentByArticleIdParam();
		findCommentByArticleIdParam.setArticleId(articleId);
		findCommentByArticleIdParam.setStartTime(controllerParam.getStartTime());
		findCommentByArticleIdParam.setLimit(5);
		if(controllerParam.getHasAdminRole()) {
			findCommentByArticleIdParam.setIsPass(controllerParam.getIsPass());
			findCommentByArticleIdParam.setIsDelete(controllerParam.getIsDelete());
		}
		List<FindCommentByArticleIdBO> commentBOList = articleCommentMapper.findCommentByArticleId(findCommentByArticleIdParam);
		if(commentBOList == null || commentBOList.size() < 1) {
			result.setIsSuccess();
			ArticleCommentVO vo = new ArticleCommentVO();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			vo.setContentLines(Arrays.asList(new String[] {"暂时还没有更多评论"}));
			vo.setCreateTimeStr(sdf.format(new Date()));
			commentVOList.add(vo);
			result.setCommentList(commentVOList);
			return result;
		}
		
		List<Long> commentIdList = new ArrayList<Long>();
		commentBOList.stream().forEach(bo -> commentIdList.add(bo.getArticleCommentId()));
		
		Map<Long, ArticleEvaluationStatisticsVO> evaluationStatisticsMap = articleEvaluationService.findEvaluationStatisticsByArticleId(ArticleEvaluationType.articleCommentEvaluation, commentIdList);
		commentBOList.stream().forEach(bo -> commentVOList.add(fillArticleCommentFromBo(evaluationStatisticsMap, bo)));
		
		result.setIsSuccess();
		result.setCommentList(commentVOList);
		
		
		return result;
	}
	
	/**
	 * 2019-12-06
	 * 原始方法 当时未采用富文本编辑器
	 * 现已准备废弃
	 * @param evaluationStatisticsMap
	 * @param bo
	 * @return
	 */
	private ArticleCommentVO fillArticleCommentFromBo(Map<Long, ArticleEvaluationStatisticsVO> evaluationStatisticsMap, FindCommentByArticleIdBO bo) {
		ArticleCommentVO vo = new ArticleCommentVO();
		String strContent = ioUtil.getStringFromFile(bo.getPath());
		List<String> strLines = Arrays.asList(strContent.split("\n"));
		List<String> outputLines = new ArrayList<String>();
		String line = "";
		for(int i = 0; i < strLines.size(); i++) {
			line = strLines.get(i);
			outputLines.add(line);
		}
		vo.setContentLines(outputLines);
		vo.setEvaluationCodeAndCount(evaluationStatisticsMap.get(bo.getArticleCommentId()).getEvaluationCodeAndCount());
		vo.setNickName(bo.getNickName());
		vo.setCreateTimeStr(createDateDescription(bo.getCreateTime()));
		vo.setArticleCommentId(bo.getArticleCommentId());
		vo.setIsPass(bo.getIsPass());
		vo.setIsDelete(bo.getIsDelete());
		vo.setIsReject(bo.getIsReject());
		
		return vo;
	}
	
	@Override
	public List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.size() < 1) {
			return new ArrayList<ArticleCommentCountByArticleIdBO>();
		}
		
		return articleCommentMapper.findCommentCountByArticleId(articleIdList);
	}
} 
