package demo.article.articleComment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auxiliaryCommon.pojo.result.CommonResult;
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

@Service
public class ArticleCommentAdminServiceImpl extends ArticleCommonService implements ArticleCommentAdminService {
	
//	@Autowired
//	private ArticleEvaluationService articleEvaluationService;
	
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private ArticleCommentReviewMapper articleCommentReviewMapper;
	
	private CommonResult ArticleCommentReviewDTOValider(ArticleCommentReviewCommonDTO dto) {
		CommonResult result = new CommonResult();
		
		if(StringUtils.isBlank(dto.getPk())) {
			result.setMessage("Error param");
			return result;
		}
		
		result.setIsSuccess();
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResult deleteArticleComment(DeleteArticleCommentDTO param) {
		CommonResult result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.setMessage("Error param");
			return result;
		}
		
		Long commentId = systemOptionService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.setMessage("Error param");
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
		articleCommentReviewMapper.insertOrUpdate(reviewRecord);
		
		
		record.setIsDelete(true);
		articleCommentMapper.updateByPrimaryKeySelective(record);
		result.setMessage(ArticleCommentResultType.articleCommentDeleteSuccess.getName());
		result.setIsSuccess();
		
		articleCommentCountingDown(record.getArticleId());
		
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResult passArticleComment(PassArticleCommentDTO param) {
		CommonResult result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.setMessage("Error param");
			return result;
		}
		
		Long commentId = systemOptionService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.setMessage("Error param");
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
		articleCommentReviewMapper.insertOrUpdate(reviewRecord);
		
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
	public CommonResult rejectArticleComment(RejectArticleCommentDTO param) {
		CommonResult result = ArticleCommentReviewDTOValider(param);
		if(result.isFail()) {
			return result;
		}
		
		Long reviewerId = baseUtilCustom.getUserId();
		if(reviewerId == null ) {
			result.setMessage("Error param");
			return result;
		}
		
		Long commentId = systemOptionService.decryptPrivateKey(param.getPk());
		if(commentId == null) {
			result.setMessage("Error param");
			return result;
		}
		
		ArticleCommentReview reviewRecord = new ArticleCommentReview();
		reviewRecord.setCommentId(commentId);
		reviewRecord.setReviewTypeId(ArticleCommentReviewType.reject.getReviewCode());
		reviewRecord.setArticleReviewerId(reviewerId);
		articleCommentReviewMapper.insertOrUpdate(reviewRecord);
		
		ArticleComment record = new ArticleComment();
		record.setId(commentId);
		record.setIsReject(true);
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
