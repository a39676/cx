package demo.article.articleComment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.articleComment.mapper.ArticleCommentMapper;
import demo.article.articleComment.mapper.ArticleCommentReviewMapper;
import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.po.ArticleComment;
import demo.article.articleComment.pojo.po.ArticleCommentReview;
import demo.article.articleComment.pojo.type.ArticleCommentReviewType;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;

@Service
public class ArticleCommentAdminServiceImpl extends CommonService implements ArticleCommentAdminService {
	
//	@Autowired
//	private ArticleEvaluationService articleEvaluationService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private ArticleCommentReviewMapper articleCommentReviewMapper;
	
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX deleteArticleComment(DeleteArticleCommentDTO param) {
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
		
		
		ArticleComment record = new ArticleComment();
		record.setId(param.getCommentId());
		record.setIsDelete(true);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.fillWithResult(ResultTypeCX.articleCommentDeleteSuccess);
		
		return result;
	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX passArticleComment(PassArticleCommentDTO param) {
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
		
		ArticleComment record = new ArticleComment();
		record.setId(param.getCommentId());
		record.setIsPass(true);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.fillWithResult(ResultTypeCX.articleCommentPassSuccess);
		
		return result;
	}
	
	@Override
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.isEmpty()) {
			return new ArrayList<Long>();
		}
		
		return 	articleCommentMapper.findArticleIdWithCommentWaitingForReview(false, articleIdList);
	}
}
