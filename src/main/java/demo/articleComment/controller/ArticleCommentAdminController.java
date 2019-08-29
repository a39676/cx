package demo.articleComment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
import demo.articleComment.pojo.param.controllerParam.DeleteArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.PassArticleCommentParam;
import demo.articleComment.service.ArticleCommentAdminService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ArticleAdminCommentUrlConstant.root)
public class ArticleCommentAdminController extends CommonController {

	@Autowired
	private ArticleCommentAdminService articleCommentAdminService;

	@PostMapping(value = ArticleAdminCommentUrlConstant.deleteArticleComment)
	public void deleteArticleComment(@RequestBody String data, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		DeleteArticleCommentParam param = new DeleteArticleCommentParam().fromJson(getJson(data));
		CommonResult result = articleCommentAdminService.deleteArticleComment(param);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = ArticleAdminCommentUrlConstant.passArticleComment)
	public void passArticleComment(@RequestBody String data, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PassArticleCommentParam param = new PassArticleCommentParam().fromJson(getJson(data));
		CommonResult result = articleCommentAdminService.passArticleComment(param);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		return articleCommentAdminService.findArticleIdWithCommentWaitingForReview(articleIdList);
	}
	
}
