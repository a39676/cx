package demo.article.articleComment.service;

import java.util.List;

import demo.article.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.article.articleComment.pojo.param.controllerParam.PassArticleCommentParam;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentAdminService {

	CommonResultCX deleteArticleComment(DeleteArticleCommentParam param);

	CommonResultCX passArticleComment(PassArticleCommentParam param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}