package demo.article.articleComment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.article.service.impl.ArticleCommonService;
import demo.article.articleComment.mapper.ArticleCommentMapper;
import demo.article.articleComment.mapper.ArticleCommentReviewMapper;
import demo.article.articleComment.pojo.dto.ArticleCommentReviewCommonDTO;
import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.dto.RejectArticleCommentDTO;
import demo.article.articleComment.pojo.po.ArticleComment;
import demo.article.articleComment.pojo.po.ArticleCommentExample;
import demo.article.articleComment.pojo.po.ArticleCommentReview;
import demo.article.articleComment.pojo.type.ArticleCommentResultType;
import demo.article.articleComment.pojo.type.ArticleCommentReviewType;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;

@Service
public class ArticleCommentAdminServiceImpl extends ArticleCommonService implements ArticleCommentAdminService {
	
//	@Autowired
//	private ArticleEvaluationService articleEvaluationService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private ArticleCommentReviewMapper articleCommentReviewMapper;
	
	private CommonResultCX ArticleCommentReviewDTOValider(ArticleCommentReviewCommonDTO dto) {
		CommonResultCX result = new CommonResultCX();
		
		if(StringUtils.isBlank(dto.getPk())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		result.setIsSuccess();
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResultCX deleteArticleComment(DeleteArticleCommentDTO param) {
		CommonResultCX result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long commentId = systemConstantService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		ArticleComment record = articleCommentMapper.selectByPrimaryKey(commentId);
		if(record == null) {
			result.setMessage(ArticleCommentResultType.articleCommentDeleteError.getName());
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(commentId);
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.delete.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertNew(reviewRecord);
		
		
		record.setIsDelete(true);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.setMessage(ArticleCommentResultType.articleCommentDeleteSuccess.getName());
		result.setIsSuccess();
		
		articleCommentCountingDown(record.getArticleId());
		
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResultCX passArticleComment(PassArticleCommentDTO param) {
		CommonResultCX result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long commentId = systemConstantService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleComment record = articleCommentMapper.selectByPrimaryKey(commentId);
		if(record == null) {
			result.setMessage(ArticleCommentResultType.articleCommentPassError.getName());
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(commentId);
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.pass.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertNew(reviewRecord);
		
		record.setIsPass(true);
		record.setIsDelete(false);
		record.setIsReject(false);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.setMessage(ArticleCommentResultType.articleCommentPassSuccess.getName());
		result.setIsSuccess();
		
		articleCommentCountingUp(record.getArticleId());
		
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResultCX rejectArticleComment(RejectArticleCommentDTO param) {
		CommonResultCX result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long commentId = systemConstantService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(commentId);
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.reject.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertNew(reviewRecord);
		
		ArticleComment record = new ArticleComment();
		record.setId(commentId);
		record.setIsPass(true);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.setMessage(ArticleCommentResultType.articleCommentRejectSuccess.getName());
		result.setIsSuccess();
		
		return result;
	}
	
	@Override
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		if(articleIdList == null || articleIdList.isEmpty()) {
			return new ArrayList<Long>();
		}
		
		ArticleCommentExample example = new ArticleCommentExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIsRejectEqualTo(false).andIsPassEqualTo(false).andArticleIdIn(articleIdList);
		List<ArticleComment> commentPOList = articleCommentMapper.selectByExample(example);
		List<Long> articleIDList = commentPOList.stream().map(po -> po.getArticleId()).collect(Collectors.toList());
		return articleIDList;
	}
	
}
