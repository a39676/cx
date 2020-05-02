package demo.article.articleComment.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentService {

	CommonResultCX creatingArticleComment(HttpServletRequest request, CreateArticleCommentDTO inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageDTO controllerParam);

	List<ArticleCommentCount> findCommentCountByArticleId(List<Long> articleIdList);

}
