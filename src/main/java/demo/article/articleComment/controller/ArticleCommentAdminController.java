package demo.article.articleComment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.dto.RejectArticleCommentDTO;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.common.controller.CommonController;

@Controller
@RequestMapping(value = ArticleAdminCommentUrlConstant.root)
public class ArticleCommentAdminController extends CommonController {

	@Autowired
	private ArticleCommentAdminService articleCommentAdminService;

	@PostMapping(value = ArticleAdminCommentUrlConstant.deleteArticleComment)
	@ResponseBody
	public CommonResult deleteArticleComment(@RequestBody DeleteArticleCommentDTO param) {
		CommonResult result = articleCommentAdminService.deleteArticleComment(param);
		return result;
	}
	
	@PostMapping(value = ArticleAdminCommentUrlConstant.passArticleComment)
	@ResponseBody
	public CommonResult passArticleComment(@RequestBody PassArticleCommentDTO param) {
		return articleCommentAdminService.passArticleComment(param);
	}
	
	@PostMapping(value = ArticleAdminCommentUrlConstant.rejectArticleComment)
	@ResponseBody
	public CommonResult rejectArticleComment(@RequestBody RejectArticleCommentDTO param) {
		return articleCommentAdminService.rejectArticleComment(param);
	}
	
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		return articleCommentAdminService.findArticleIdWithCommentWaitingForReview(articleIdList);
	}
	
}
