package demo.article.articleComment.service;

import java.io.IOException;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import jakarta.servlet.http.HttpServletRequest;

public interface ArticleCommentService {

	CommonResult creatingArticleComment(HttpServletRequest request, CreateArticleCommentDTO inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentVOPage(FindArticleCommentPageDTO dto);

	List<ArticleCommentCount> findCommentCountByArticleId(List<Long> articleIdList);

	/**
	 * 供手动调用
	 * 根据当前黑名单ip, 将关联评论 isReject = true
	 */
	void batchRejectComment();

	void articleCommentCountingUp(Long articleId);

	void articleCommentCountingDown(Long articleId);
}
