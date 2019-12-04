package demo.article.articleComment.service;

import java.io.IOException;
import java.util.List;

import demo.article.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.article.articleComment.pojo.param.controllerParam.CreateArticleCommentParam;
import demo.article.articleComment.pojo.param.controllerParam.FindArticleCommentPageParam;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentService {

	CommonResultCX creatingArticleComment(Long userId, CreateArticleCommentParam inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageParam controllerParam);

	List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList);

}
