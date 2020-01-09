package demo.article.articleComment.service;

import java.util.List;

import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentAdminService {

	CommonResultCX deleteArticleComment(DeleteArticleCommentDTO param);

	CommonResultCX passArticleComment(PassArticleCommentDTO param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}
