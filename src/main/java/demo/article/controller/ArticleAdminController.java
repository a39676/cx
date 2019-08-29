package demo.article.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.article.pojo.constant.ArticleAdminUrlConstant;
import demo.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.pojo.type.ArticleReviewType;
import demo.article.pojo.vo.ArticleChannelVO;
import demo.article.service.ArticleAdminService;
import demo.article.service.ArticleChannelService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping( value = ArticleAdminUrlConstant.root)
public class ArticleAdminController extends CommonController {
	
	@Autowired
	private ArticleAdminService articleAdminService;
	
//	@Autowired
//	private ArticleService articleService;
	@Autowired
	private ArticleChannelService channelService;
	
//	@Autowired
//	private BaseUtilCustom baseUtilCustom;
	
	@PostMapping(value = ArticleAdminUrlConstant.loadArticleUUIDChannel)
	public void loadArticleUUIDChannel(HttpServletRequest request, HttpServletResponse response) {
		boolean flag = channelService.loadArticleUUIDChannel(true);
		CommonResult serviceResult = new CommonResult();
		if(!flag) {
			serviceResult.fillWithResult(ResultType.serviceError);
		} else {
			serviceResult.fillWithResult(ResultType.success);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
		return;
	}

	@PostMapping(value = ArticleAdminUrlConstant.batchUpdatePrivateKey)
	public void batchUpdatePrivateKey(@RequestBody BatchUpdatePrimaryKeyParam param, HttpServletRequest request, HttpServletResponse response) {
		CommonResult serviceResult = articleAdminService.batchUpdatePrivateKey(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
		return;
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.passArticle)
	public void passArticle(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		ReviewArticleLongParam param = new ReviewArticleLongParam().fromJson(jsonInput);
		param.setReviewCode(ArticleReviewType.pass.getReviewCode());
		CommonResult serviceResult = new CommonResult();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultType.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.rejectArticle)
	public void rejectArticle(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		ReviewArticleLongParam param = new ReviewArticleLongParam().fromJson(jsonInput);
		param.setReviewCode(ArticleReviewType.reject.getReviewCode());
		CommonResult serviceResult = new CommonResult();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultType.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.deleteArticle)
	public void deleteArticle(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		ReviewArticleLongParam param = new ReviewArticleLongParam().fromJson(jsonInput);
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());
		CommonResult serviceResult = new CommonResult();
		try {
			serviceResult = articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.fillWithResult(ResultType.serviceError);
		}
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.findArticleChannel)
	public void findAllChannel(HttpServletRequest request, HttpServletResponse response) {
		FindArticleChannelsParam param = new FindArticleChannelsParam();
		List<ArticleChannelVO> channelList = articleAdminService.findChannel(param);
		outputJson(response, JSONArray.fromObject(channelList));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.changeChannel)
	public void changeChannel(@RequestBody ChangeChannelParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonResult serviceResult = articleAdminService.changeChannel(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.setArticleHot)
	public void setArticleHot(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		SetArticleHotParam param = new SetArticleHotParam().fromJson(getJson(data));
		CommonResult serviceResult = articleAdminService.setArticleHot(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
}
