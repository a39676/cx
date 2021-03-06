package demo.article.articleComment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.article.articleComment.pojo.constant.ArticleAdminCommentUrlConstant;
import demo.article.articleComment.pojo.dto.DeleteArticleCommentDTO;
import demo.article.articleComment.pojo.dto.PassArticleCommentDTO;
import demo.article.articleComment.pojo.dto.RejectArticleCommentDTO;
import demo.article.articleComment.service.ArticleCommentAdminService;
import demo.common.controller.CommonController;
import demo.common.pojo.result.CommonResultCX;

@Controller
@RequestMapping(value = ArticleAdminCommentUrlConstant.root)
public class ArticleCommentAdminController extends CommonController {

	@Autowired
	private ArticleCommentAdminService articleCommentAdminService;

	@PostMapping(value = ArticleAdminCommentUrlConstant.deleteArticleComment)
	@ResponseBody
	public CommonResultCX deleteArticleComment(@RequestBody DeleteArticleCommentDTO param) {
		CommonResultCX result = articleCommentAdminService.deleteArticleComment(param);
		return result;
	}
	
	@PostMapping(value = ArticleAdminCommentUrlConstant.passArticleComment)
	@ResponseBody
	public CommonResultCX passArticleComment(@RequestBody PassArticleCommentDTO param) {
		return articleCommentAdminService.passArticleComment(param);
	}
	
	@PostMapping(value = ArticleAdminCommentUrlConstant.rejectArticleComment)
	@ResponseBody
	public CommonResultCX rejectArticleComment(@RequestBody RejectArticleCommentDTO param) {
		return articleCommentAdminService.rejectArticleComment(param);
	}
	
	public List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList) {
		return articleCommentAdminService.findArticleIdWithCommentWaitingForReview(articleIdList);
	}
	
}
