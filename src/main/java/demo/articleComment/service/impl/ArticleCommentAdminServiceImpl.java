package demo.articleComment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.articleComment.mapper.ArticleCommentMapper;
import demo.articleComment.mapper.ArticleCommentReviewMapper;
import demo.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.PassArticleCommentParam;
import demo.articleComment.pojo.po.ArticleCommentReview;
import demo.articleComment.pojo.type.ArticleCommentReviewType;
import demo.articleComment.service.ArticleCommentAdminService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import demo.util.BaseUtilCustom;

@Service
public class ArticleCommentAdminServiceImpl extends CommonService implements ArticleCommentAdminService {
	
//	@Autowired
//	private SystemConstantService systemConstantService;
//	@Autowired
//	private ArticleEvaluationService articleEvaluationService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private ArticleCommentReviewMapper articleCommentReviewMapper;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX deleteArticleComment(DeleteArticleCommentParam param) {
		CommonResultCX result = new CommonResultCX();
		
		if(param.getCommentId() == null || param.getCommentId() < 0) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(param.getCommentId());
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.delete.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertNew(reviewRecord);
		
		articleCommentMapper.logicDelete(param.getCommentId());
		result.fillWithResult(ResultTypeCX.articleCommentDeleteSuccess);
		
		return result;
	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX passArticleComment(PassArticleCommentParam param) {
		CommonResultCX result = new CommonResultCX();
		
		if(param.getCommentId() == null || param.getCommentId() < 0) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(param.getCommentId());
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.pass.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertNew(reviewRecord);
		
		articleCommentMapper.passComment(param.getCommentId());
		result.fillWithResult(ResultTypeCX.articleCommentPassSuccess);
		
		return result;
	}
	
	@Override
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.size() < 1) {
			return new ArrayList<Long>();
		}
		return articleCommentMapper.findArticleIdWithCommentWaitingForReview(articleIdList);
	}
}
