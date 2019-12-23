package demo.article.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.constant.ArticleAdminUrlConstant;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleChannelService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import net.sf.json.JSONObject;

@Controller
@RequestMapping( value = ArticleAdminUrlConstant.root)
public class ArticleAdminController extends CommonController {
	
	@Autowired
	private ArticleAdminService articleAdminService;
	@Autowired
	private ArticleChannelService channelService;
	
	@PostMapping(value = ArticleAdminUrlConstant.findArticleChannel)
	@ResponseBody
	public List<ArticleChannelVO> findArticleChannel() {
		return channelService.findArticleChannel();
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.batchUpdatePrivateKey)
	public void batchUpdatePrivateKey(@RequestBody BatchUpdatePrimaryKeyParam param, HttpServletRequest request, HttpServletResponse response) {
		CommonResult serviceResult = articleAdminService.batchUpdatePrivateKey(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
		return;
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.passArticle)
	public void passArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.pass.getReviewCode());
		CommonResultCX serviceResult = new CommonResultCX();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultTypeCX.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.rejectArticle)
	public void rejectArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.reject.getReviewCode());
		CommonResultCX serviceResult = new CommonResultCX();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultTypeCX.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.deleteArticle)
	public void deleteArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());
		CommonResultCX serviceResult = new CommonResultCX();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultTypeCX.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.changeChannel)
	public void changeChannel(@RequestBody ChangeChannelParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonResult serviceResult = articleAdminService.changeChannel(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.setArticleHot)
	public void setArticleHot(@RequestBody SetArticleHotParam param, HttpServletRequest request, HttpServletResponse response) {
		CommonResult serviceResult = articleAdminService.setArticleHot(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@GetMapping(value = ArticleAdminUrlConstant.articleChannelManager)
	public ModelAndView articleChannelManager() {
		return channelService.articleChannelManagerView();
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.articleChannelManager)
	@ResponseBody
	public CommonResultCX articleChannelManager(@RequestBody ArticleChannelManagerDTO dto) {
		return channelService.articleChannelManager(dto);
	}
}
