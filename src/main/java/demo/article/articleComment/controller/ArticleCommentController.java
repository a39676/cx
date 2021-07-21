package demo.article.articleComment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.article.articleComment.pojo.constant.ArticleCommentUrlConstant;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.article.articleComment.service.ArticleCommentService;
import demo.common.controller.CommonController;
import demo.common.pojo.result.CommonResultCX;

@Controller
@RequestMapping( value = ArticleCommentUrlConstant.root)
public class ArticleCommentController extends CommonController {
	
	@Autowired
	private ArticleCommentService articleCommentService;
	
	@PostMapping(value = ArticleCommentUrlConstant.createArticleComment)
	@ResponseBody
	public CommonResultCX createArticleComment(@RequestBody CreateArticleCommentDTO param, HttpServletRequest request) throws IOException {
		return articleCommentService.creatingArticleComment(request, param);
	}
	
	@PostMapping(value = ArticleCommentUrlConstant.findArticleCommentPage)
	@ResponseBody
	public FindArticleCommentPageResult findArticleCommentPage(@RequestBody FindArticleCommentPageDTO param) {
		return articleCommentService.findArticleCommentVOPage(param);
	}
	
	public List<ArticleCommentCount> findCommentCountByArticleId(List<Long> articleIdList) {
		return articleCommentService.findCommentCountByArticleId(articleIdList);
	}
}
