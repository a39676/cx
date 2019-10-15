package demo.articleComment.service;

import java.io.IOException;
import java.util.List;

import demo.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.articleComment.pojo.param.controllerParam.CreateArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.FindArticleCommentPageParam;
import demo.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentService {

	CommonResultCX creatingArticleComment(Long userId, CreateArticleCommentParam inputParam) throws IOException;

	FindArticleCommentPageResult findArticleCommentPage(FindArticleCommentPageParam controllerParam);

	List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList);

}
