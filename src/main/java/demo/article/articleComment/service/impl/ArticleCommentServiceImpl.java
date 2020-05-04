package demo.article.articleComment.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

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
import demo.article.articleComment.mapper.ArticleCommentCountMapper;
import demo.article.articleComment.mapper.ArticleCommentMapper;
import demo.article.articleComment.pojo.constant.ArticleCommentConstant;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.dto.FindCommentPageDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.po.ArticleComment;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.po.ArticleCommentCountExample;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.article.articleComment.service.ArticleCommentService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UserDetailService;
import demo.base.user.service.UsersService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.tool.service.ValidRegexToolService;
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
	private UsersService usersService;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private ArticleCommentCountMapper articleCommentCountMapper;
	
	@Autowired
	private FileUtilCustom ioUtil;
	
	private String loadArticleCommentStorePath() {
		String path = constantService.getValByName(ArticleCommentConstant.commentStorePathRedisKey);
		
		if(StringUtils.isNotBlank(path)) {
			return path;
		}
		
		if(isLinux()) {
			path = "/home/u2/articleComment";
		} else {
			path = "d:/home/u2/articleComment";
		}
		
		constantService.setValByName(ArticleCommentConstant.commentStorePathRedisKey, path);
		
		return path;
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
	public CommonResultCX creatingArticleComment(HttpServletRequest request, CreateArticleCommentDTO inputParam) throws IOException {
		/* 
		 * TODO 准备放开未登录留言
		 */
		
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(inputParam.getContent())) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		if(inputParam.getContent().replaceAll("\\s", "").length() < 6) {
			result.fillWithResult(ResultTypeCX.articleTooShort);
			return result;
		}
		String articleCommentStorePrefixPath = loadArticleCommentStorePath();
		if(StringUtils.isBlank(articleCommentStorePrefixPath) || loadMaxArticleLength() <= 0) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		Long articleId = decryptPrivateKey(inputParam.getPk());
		
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		boolean bigUserFlag = isBigUser();
		Long userId = baseUtilCustom.getUserId();
		PolicyFactory filter = textFilter.getArticleFilter();
		
		String nickname = null;
		String email = null;
		if(userId == null) {
			nickname = filter.sanitize(inputParam.getNickname());
			email = filter.sanitize(inputParam.getEmail());
			if(userDetailService.isNicknameExists(nickname)) {
				result.failWithMessage("此昵称已注册...如需使用此昵称..请登录");
				return result;
			}
			
			if(!validRegexToolService.validEmail(email) || userDetailService.ensureActiveEmail(email).isSuccess()) {
				result.failWithMessage("此邮箱已注册...如需使用此邮箱..请登录");
				return result;
			}
		} else {
			UsersDetailVO userDetailVO = usersService.findUserDetail(userId);
			nickname = userDetailVO.getNickName();
			email = userDetailVO.getEmail();
		}
		
		if(!bigUserFlag) {
			if(justComment(request, userId, articleId)) {
				result.fillWithResult(ResultTypeCX.justComment);
				return result;
			}
			inputParam.setContent(filter.sanitize(inputParam.getContent()));
		} 

		ArticleFileSaveResult saveFileResult = saveArticleCommentFile(articleCommentStorePrefixPath, userId, inputParam.getContent());
		if(!saveFileResult.isSuccess()) {
			return saveFileResult;
		}
		
		ArticleComment newComment = new ArticleComment();
		Long newCommonId = snowFlake.getNextId();
		newComment.setId(newCommonId);
		newComment.setArticleId(articleId);
		newComment.setPath(saveFileResult.getFilePath());
		newComment.setUserId(userId);
		articleCommentMapper.insertSelective(newComment);
		
		if(bigUserFlag) {
			PassArticleCommentDTO param = new PassArticleCommentDTO();
			param.setCommentId(newCommonId);
			articleCommentAdminService.passArticleComment(param );
		} else {
			insertCommentRedisMark(request, userId, articleId);
		}
		
		articleCommentCountingUp(articleId);
		result.setIsSuccess();
		
		return result;
	}
	
	private ArticleFileSaveResult saveArticleCommentFile(String storePrefixPath, Long userId, String content) throws IOException {
		return articleService.saveArticleFile(storePrefixPath, userId, content);
	}
	
	@Override
	public FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageDTO controllerParam) {
		FindArticleCommentPageResult result = new FindArticleCommentPageResult();
		List<ArticleCommentVO> commentVOList = new ArrayList<ArticleCommentVO>();
		
		if(StringUtils.isBlank(controllerParam.getPk())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(controllerParam.getStartTime() != null && controllerParam.getStartTime().isBefore(theStartTime)) {
			controllerParam.setStartTime(theStartTime);
		}
		if(controllerParam.getStartTime() != null) {
			controllerParam.setStartTime(controllerParam.getStartTime().plusSeconds(1L));
		}
		
		Long articleId = decryptPrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		result.setPk(controllerParam.getPk());
		

		if(!isBigUser()) {
			controllerParam.setIsDelete(false);
			controllerParam.setIsPass(true);
		}
		
		FindCommentPageDTO mapperDTO = new FindCommentPageDTO();
		mapperDTO.setArticleId(articleId);
		mapperDTO.setStartTime(controllerParam.getStartTime());
		mapperDTO.setIsDelete(controllerParam.getIsDelete());
		mapperDTO.setIsPass(controllerParam.getIsPass());
		
		List<ArticleComment> commentPOList = articleCommentMapper.findCommentPage(mapperDTO);
		if(commentPOList == null || commentPOList.isEmpty()) {
			result.setIsSuccess();
			ArticleCommentVO vo = new ArticleCommentVO();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			vo.setContent("暂时还没有更多评论");
			vo.setCreateTimeStr(sdf.format(new Date()));
			commentVOList.add(vo);
			result.setCommentList(commentVOList);
			return result;
		}
		
		List<Long> commentIdList = new ArrayList<Long>();
		commentPOList.stream().forEach(bo -> commentIdList.add(bo.getId()));
		
		Map<Long, ArticleEvaluationStatisticsVO> evaluationStatisticsMap = articleEvaluationService.findEvaluationStatisticsByArticleId(ArticleEvaluationType.articleCommentEvaluation, commentIdList);
		commentPOList.stream().forEach(po -> commentVOList.add(fillArticleCommentFromBo(evaluationStatisticsMap, po)));
		
		result.setIsSuccess();
		result.setCommentList(commentVOList);
		
		
		return result;
	}
	
	private ArticleCommentVO fillArticleCommentFromBo(Map<Long, ArticleEvaluationStatisticsVO> evaluationStatisticsMap, ArticleComment po) {
		ArticleCommentVO vo = new ArticleCommentVO();
		String content = ioUtil.getStringFromFile(po.getPath());
		vo.setContent(content);
		vo.setEvaluationCodeAndCount(evaluationStatisticsMap.get(po.getId()).getEvaluationCodeAndCount());
		vo.setNickName(po.getTmpNickName());
		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		vo.setArticleCommentId(po.getId());
		vo.setIsPass(po.getIsPass());
		vo.setIsDelete(po.getIsDelete());
		vo.setIsReject(po.getIsReject());
		
		return vo;
	}
	
	@Override
	public List<ArticleCommentCount> findCommentCountByArticleId(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.size() < 1) {
			return new ArrayList<ArticleCommentCount>();
		}
		
		ArticleCommentCount tmpBO = null;
		boolean idExistsFlag = false;
		ArticleCommentCountExample example = new ArticleCommentCountExample();
		example.createCriteria().andArticleIdIn(articleIdList);
		List<ArticleCommentCount> resultList = articleCommentCountMapper.selectByExample(example);
		for(Long articleId : articleIdList) {
			for(int i = 0; i < resultList.size() && !idExistsFlag; i++) {
				tmpBO = resultList.get(i);
				if(tmpBO.getArticleId().equals(articleId)) {
					idExistsFlag = true;
				}
			}
			if(!idExistsFlag) {
				tmpBO = new ArticleCommentCount();
				tmpBO.setArticleId(articleId);
				tmpBO.setCounting(0L);
				resultList.add(tmpBO);
			}
			idExistsFlag = false;
		}
		return resultList;
	}

	private boolean justComment(HttpServletRequest request, Long userId, Long articleId) {
		String keyPrefix = SystemRedisKey.articleCommentKeyPrefix + "_" + userId + "_" + articleId;
		
		int counting = checkFunctionalModuleVisitData(request, keyPrefix);
		if(counting > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private void insertCommentRedisMark(HttpServletRequest request, Long userId, Long articleId) {
		String keyPrefix = SystemRedisKey.articleCommentKeyPrefix + "_" + userId + "_" + articleId;
		insertFunctionalModuleVisitData(request, keyPrefix, 10L, TimeUnit.MINUTES);
	}
	
	private void articleCommentCountingUp(Long articleId) {
		ArticleCommentCount po = articleCommentCountMapper.selectByPrimaryKey(articleId);
		if(po == null) {
			po = new ArticleCommentCount();
			po.setArticleId(articleId);
			po.setCounting(1L);
			articleCommentCountMapper.insertSelective(po);
		} else {
			po.setCounting(po.getCounting() + 1);
			articleCommentCountMapper.updateByPrimaryKey(po);
		}
		
	}
} 
