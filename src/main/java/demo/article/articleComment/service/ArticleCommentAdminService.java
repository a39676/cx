package demo.article.articleComment.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.dto.RejectArticleCommentDTO;

public interface ArticleCommentAdminService {

	CommonResult deleteArticleComment(DeleteArticleCommentDTO param);

	CommonResult passArticleComment(PassArticleCommentDTO param);
	
	CommonResult rejectArticleComment(RejectArticleCommentDTO param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}
