package demo.articleComment.service;

import java.util.List;

import demo.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.PassArticleCommentParam;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleCommentAdminService {

	CommonResultCX deleteArticleComment(DeleteArticleCommentParam param);

	CommonResultCX passArticleComment(PassArticleCommentParam param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}
