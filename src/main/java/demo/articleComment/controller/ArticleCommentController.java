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
import org.springframework.web.servlet.ModelAndView;

import demo.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.articleComment.pojo.constant.ArticleCommentUrlConstant;
import demo.articleComment.pojo.param.controllerParam.CreateArticleCommentParam;
import demo.articleComment.pojo.param.controllerParam.FindArticleCommentPageParam;
import demo.articleComment.pojo.result.FindArticleCommentPageResult;
import demo.articleComment.service.ArticleCommentService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONObject;

@Controller
@RequestMapping( value = ArticleCommentUrlConstant.root)
public class ArticleCommentController extends CommonController {
	
	@Autowired
	private ArticleCommentService articleCommentService;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@PostMapping(value = ArticleCommentUrlConstant.createArticleComment)
	public void createArticleComment(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		TODO
		CreateArticleCommentParam param = new CreateArticleCommentParam().fromJson(getJson(data));
		CommonResultCX result = articleCommentService.creatingArticleComment(baseUtilCustom.getUserId(), param);
		
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = ArticleCommentUrlConstant.findArticleCommentPage)
	public ModelAndView findArticleCommentPage(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
//		TODO
		ModelAndView view = new ModelAndView("articleJSP/articleCommentList");
		FindArticleCommentPageParam param = new FindArticleCommentPageParam().fromJson(getJson(data));
		if(baseUtilCustom.hasAdminRole()) {
			param.setHasAdminRole(true);
		} else {
			param.setIsDelete(false);
			param.setIsPass(true);
		}
		FindArticleCommentPageResult result = articleCommentService.findArticleCommentPage(param);
		if(!result.isSuccess()) {
			view.addObject("message", result.getMessage());
			return view;
		}
		
		view.addObject("commentList", result.getCommentList());
		view.addObject("pk", result.getPk());
		if(result.getCommentList() != null && result.getCommentList().size() > 0) {
			view.addObject("startTime", result.getCommentList().get(result.getCommentList().size() - 1).getCreateTimeStr());
		}
		
		return view;
	}
	
	@PostMapping(value = ArticleCommentUrlConstant.findArticleCommentSubPage)
	public ModelAndView findArticleCommentSubPage(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
//		TODO
		ModelAndView view = new ModelAndView("articleJSP/articleCommentListSubList");
		FindArticleCommentPageParam param = new FindArticleCommentPageParam().fromJson(getJson(data));
		if(baseUtilCustom.hasAdminRole()) {
			param.setHasAdminRole(true);
		} else {
			param.setIsDelete(false);
			param.setIsPass(true);
		}
		FindArticleCommentPageResult result = articleCommentService.findArticleCommentPage(param);
		if(!result.isSuccess()) {
			view.addObject("message", result.getMessage());
			return view;
		}
		
		view.addObject("commentList", result.getCommentList());
		view.addObject("pk", result.getPk());
		if(result.getCommentList() != null && result.getCommentList().size() > 0) {
			view.addObject("startTime", result.getCommentList().get(result.getCommentList().size() - 1).getCreateTimeStr());
		}
		
		return view;
	}

	public List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList) {
		return articleCommentService.findCommentCountByArticleId(articleIdList);
	}
}
