package demo.articleComment.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.PassArticleCommentParam;

public interface ArticleCommentAdminService {

	CommonResult deleteArticleComment(DeleteArticleCommentParam param);

	CommonResult passArticleComment(PassArticleCommentParam param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}
