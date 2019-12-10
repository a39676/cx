package demo.article.articleComment.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.vo.ArticleCommentVO;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.article.article.service.ArticleEvaluationService;
import demo.article.article.service.ArticleService;
import demo.article.articleComment.mapper.ArticleCommentMapper;
import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.article.articleComment.pojo.bo.FindCommentByArticleIdBO;
import demo.article.articleComment.pojo.param.controllerParam.CreateArticleCommentParam;
import demo.article.articleComment.pojo.param.controllerParam.FindArticleCommentPageParam;
import demo.article.articleComment.pojo.param.mapperParam.FindCommentByArticleIdParam;
import demo.article.articleComment.pojo.param.mapperParam.JustCommentParam;
import demo.article.articleComment.pojo.po.ArticleComment;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.article.articleComment.service.ArticleCommentService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleCommentServiceImpl extends CommonService implements ArticleCommentService {
	
	@Autowired
	private SystemConstantService systemConstantService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleEvaluationService articleEvaluationService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	
	@Autowired
	private FileUtilCustom ioUtil;
	
	private String articleCommentStorePrefixPath;
	private static Long maxArticleLength = 0L;
	
	private boolean loadArticleCommontStorePath() {
		articleCommentStorePrefixPath = systemConstantService.getValByName(SystemConstantStore.articleCommentStorePrefixPath);
		if (articleCommentStorePrefixPath.length() > 0) {
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
	public CommonResultCX creatingArticleComment(Long userId, CreateArticleCommentParam inputParam) throws IOException {
		CommonResultCX result = new CommonResultCX();
		if(!loadArticleCommontStorePath() || !loadMaxArticleLength()) {
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
		
		ArticleFileSaveResult saveFileResult = saveArticleCommentFile(articleCommentStorePrefixPath, userId, inputParam.getContent());
		if(!saveFileResult.isSuccess()) {
			return saveFileResult;
		}
		
		ArticleComment newComment = new ArticleComment();
		newComment.setArticleId(articleId);
		newComment.setPath(saveFileResult.getFilePath());
		newComment.setUserId(userId);
		articleCommentMapper.insertSelective(newComment);
		
		
		result.setIsSuccess();
		
		return result;
	}
	
	private ArticleFileSaveResult saveArticleCommentFile(String storePrefixPath, Long userId, String content) throws IOException {
		return articleService.saveArticleFile(storePrefixPath, userId, content);
	}
	
	@Override
	public FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageParam controllerParam) {
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
			if(line.matches(articleService.getImageHttpUrlPattern())) {
				line = "<a target=\"_blank\" href=\"" + line + "\">想看原图</a><br>"
						+ "<img style=\"width: 150px; height: 150px;\" name=\"articleImage\" fold=\"0\" src=\"" + line + "\">";
			} 
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
