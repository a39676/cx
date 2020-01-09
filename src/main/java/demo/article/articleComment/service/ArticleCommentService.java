package demo.article.articleComment.service;

import java.io.IOException;
import java.util.List;

import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.article.articleComment.pojo.dto.controllerParam.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.controllerParam.FindArticleCommentPageParam;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentService {

	CommonResultCX creatingArticleComment(Long userId, CreateArticleCommentDTO inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageParam controllerParam);

	List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList);

}
