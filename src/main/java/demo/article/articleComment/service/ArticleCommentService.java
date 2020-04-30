package demo.article.articleComment.service;

import java.io.IOException;
import java.util.List;

import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentService {

	CommonResultCX creatingArticleComment(CreateArticleCommentDTO inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageDTO controllerParam);

	List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList);

}
