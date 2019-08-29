package demo.articleComment.service;

import java.util.List;

import demo.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.PassArticleCommentParam;
import demo.baseCommon.pojo.result.CommonResult;

public interface ArticleCommentAdminService {

	CommonResult deleteArticleComment(DeleteArticleCommentParam param);

	CommonResult passArticleComment(PassArticleCommentParam param);

	List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);

}
