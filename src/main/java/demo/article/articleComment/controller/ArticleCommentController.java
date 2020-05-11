package demo.article.articleComment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.article.articleComment.pojo.constant.ArticleCommentUrlConstant;
import demo.article.articleComment.pojo.dto.CreateArticleCommentDTO;
import demo.article.articleComment.pojo.dto.FindArticleCommentPageDTO;
import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.article.articleComment.service.ArticleCommentService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;

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
	
	/**
	 * 2020-05-10 发现, 应该是旧式页面使用, 准备删除此链接
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = ArticleCommentUrlConstant.findArticleCommentSubPage)
	public ModelAndView findArticleCommentSubPage(@RequestBody FindArticleCommentPageDTO param, HttpServletRequest request, HttpServletResponse response) {
		return articleCommentService.findArticleCommentPageView(param);
	}

	public List<ArticleCommentCount> findCommentCountByArticleId(List<Long> articleIdList) {
		return articleCommentService.findCommentCountByArticleId(articleIdList);
	}
}
