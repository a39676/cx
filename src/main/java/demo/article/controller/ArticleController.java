package demo.article.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.pojo.constant.ArticleUrlConstant;
import demo.article.pojo.constant.ArticleViewConstant;
import demo.article.pojo.param.controllerParam.ArticleLongComplaintParam;
import demo.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.pojo.param.controllerParam.FindArticleLongByArticleSummaryPrivateKeyParam;
import demo.article.pojo.param.controllerParam.FindArticleLongSummaryListControllerParam;
import demo.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.pojo.param.controllerParam.LikeHateThisChannelParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.result.ArticleBurnResult;
import demo.article.pojo.result.CreatingBurnMessageResult;
import demo.article.pojo.result.GetArticleChannelsResult;
import demo.article.pojo.result.jsonRespon.FindArticleLongResult;
import demo.article.pojo.result.jsonRespon.FindArticleLongSummaryListResultV3;
import demo.article.pojo.type.ArticleEvaluationType;
import demo.article.pojo.type.ArticleReviewType;
import demo.article.service.ArticleBurnService;
import demo.article.service.ArticleChannelService;
import demo.article.service.ArticleEvaluationService;
import demo.article.service.ArticleService;
import demo.article.service.ArticleSummaryService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.util.BaseUtilCustom;
import net.sf.json.JSONObject;

@Controller
@RequestMapping( value = ArticleUrlConstant.root)
public class ArticleController extends CommonController {

	@Autowired
	private ArticleAdminController articleAdminController;
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleBurnService articleBurnService;
	@Autowired
	private ArticleEvaluationService articleEvaluationService;
	@Autowired
	private ArticleChannelService channelService;
	@Autowired
	private ArticleSummaryService summaryService;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@GetMapping(value = ArticleUrlConstant.createBurnMessage)
	public ModelAndView createBurnMessage(HttpServletRequest request) {
		visitDataService.insertVisitData(request);
		ModelAndView view = new ModelAndView(ArticleViewConstant.createBurnMessage);
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.creatingBurnMessage)
	public void createBurnMessage(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		Long userId = baseUtilCustom.getUserId();
		JSONObject jsonInput = getJson(data);
		CreatingBurnMessageResult serviceResult = articleBurnService.creatingBurnMessage(userId, jsonInput);
		if(!serviceResult.isSuccess()) {
			outputJson(response, JSONObject.fromObject(serviceResult));
			return;
		}
		
		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("readKey", serviceResult.getArticleBurn().getReadKey());
		jsonOutput.put("burnKey", serviceResult.getArticleBurn().getBurnKey());
		jsonOutput.put("readUri", ArticleUrlConstant.root + ArticleUrlConstant.readBurningMessage + "?readKey=" + serviceResult.getArticleBurn().getReadKey());
		jsonOutput.put("burnUri", ArticleUrlConstant.root + ArticleUrlConstant.burnMessage + "?burnKey=" + serviceResult.getArticleBurn().getBurnKey());
		jsonOutput.put("result", serviceResult.getResult());
		outputJson(response, jsonOutput);
	}
	
	@GetMapping(value = ArticleUrlConstant.readBurningMessage)
	public ModelAndView readBurningMessage(@RequestParam(value = "readKey", defaultValue = "" ) String readKey) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readBurningMessage);
		ArticleBurnResult pbr = articleBurnService.findArticleByReadKey(readKey);
		view.addObject("content", pbr.getContent());
		view.addObject("remainingReadCount", pbr.getReadLimit() - pbr.getReadCount() - 1);
		view.addObject("burnUri", ArticleUrlConstant.root + ArticleUrlConstant.burnMessage + "?burnKey=" + pbr.getBurnKey());
		return view;
	}
	
	@GetMapping(value = ArticleUrlConstant.burnMessage)
	public ModelAndView burnMessage(@RequestParam(value = "burnKey", defaultValue = "" ) String burnKey) {
		articleBurnService.burnArticleByBurnKey(burnKey);
		return new ModelAndView(ArticleViewConstant.burnMessage);
	}
	
	@PostMapping(value = ArticleUrlConstant.createArticleLong)
	@ResponseBody
	public CommonResult createArticleLong(@RequestBody CreateArticleParam param) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		CommonResult serviceResult = articleService.crateArticleLongPrefixServcie(param);
		return serviceResult;
	}
	
	@GetMapping(value = ArticleUrlConstant.creatingArticleLong)
	public ModelAndView creatingArticleLong(HttpServletRequest request) {
//		TODO ???what to do?
		CreatingArticleParam param = new CreatingArticleParam();
		param.setUserId(baseUtilCustom.getUserId());
		ModelAndView view = articleService.creatingArticleLong(param);
		
		String hostName = foundHostNameFromRequst(request);
		
		GetArticleChannelsResult channelsResult = getArticleChannelsDynamic(hostName);
		view.addObject("channelList", channelsResult.getChannelList());
		
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.findChannels)
	@ResponseBody
	public GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request, HttpServletResponse response) {
		String hostName = foundHostNameFromRequst(request);
		Long userId = baseUtilCustom.getUserId();
		GetArticleChannelsResult result = channelService.getArticleChannelsDynamic(hostName, userId);
//		JSONObject json = JSONObject.fromObject(result);
		return result;
	}
	
	private GetArticleChannelsResult getArticleChannelsDynamic(String hostName) {
		Long userId = baseUtilCustom.getUserId();
		return channelService.getArticleChannelsDynamic(hostName, userId);
	}
	
	@PostMapping(value = ArticleUrlConstant.articleLongSummaryListByChannel)
	public void articleLongSummaryListByChannel(@RequestBody FindArticleLongSummaryListControllerParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(baseUtilCustom.isLoginUser()) {
			param.setUserId(baseUtilCustom.getUserId());
		}
		if(!baseUtilCustom.hasAdminRole()) {
			param.setIsPass(true);
			param.setIsDelete(false);
			param.setIsEdited(false);
		} else {
			param.setHasAdminRole(true);
		}
		
		if(foundHostNameFromRequst(request).contains("site")) {
			if(StringUtils.isBlank(param.getVcode())) {
				param.setVcode("defaultVcode");
			}
		} else {
			param.setVcode(null);
		}
		
		FindArticleLongSummaryListResultV3 result = null;
		if(param.getIsHot()) {
			result = summaryService.articleLongSummaryHotListByChannelIdV3(param);
		} else {
			result = summaryService.articleLongSummaryListByChannelIdV3(param);
		}
		visitDataService.insertVisitData(request, result.getChannelId().toString());
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@GetMapping(value = ArticleUrlConstant.readArticleLong)
	public ModelAndView readArticleLong(@RequestParam(value = "", required = false) String pk, HttpServletRequest request) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readArticleLongV3);
		FindArticleLongByArticleSummaryPrivateKeyParam param = new FindArticleLongByArticleSummaryPrivateKeyParam();
		param.setPrivateKey(pk);
		if(baseUtilCustom.isLoginUser()) {
			param.setUserId(baseUtilCustom.getUserId());
		}
		FindArticleLongResult result = articleService.findArticleLongByArticleSummaryPrivateKey(param);
		view.addObject("articleLongVO", result.getArticleLongVO());
		visitDataService.insertVisitData(request, result.getArticleId().toString());
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.deleteArticle)
	public void deleteArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());
		
		CommonResultCX serviceResult = new CommonResultCX();

		if(!articleService.iWroteThis(baseUtilCustom.getUserId(), param.getPk())) {
			serviceResult.fillWithResult(ResultTypeCX.notYourArticle);
			outputJson(response, JSONObject.fromObject(serviceResult));
			return;
		}
		
		articleAdminController.deleteArticle(param, request, response);
	}
	
	@PostMapping(value = ArticleUrlConstant.insertArticleLongEvaluation)
	public void insertArticleLongEvaluation(@RequestBody InsertArticleLongEvaluationParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		CommonResult serviceResult = articleEvaluationService.insertArticleLongEvaluationRedis(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	public void insertArticleLongCommentEvaluation(@RequestBody InsertArticleLongEvaluationParam param, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * TODO
		 * 2019-06-08 发现 未明用途  待确认
		 */
		param.setEvaluationType(ArticleEvaluationType.articleCommentEvaluation.getCode());
		CommonResult serviceResult = articleEvaluationService.insertArticleLongEvaluationRedis(param);
		outputJson(response, JSONObject.fromObject(serviceResult));
	}
	
	@PostMapping(value = ArticleUrlConstant.likeOrHateThisChannel)
	public void hateThisChannel(@RequestBody LikeHateThisChannelParam param, HttpServletRequest request, HttpServletResponse response) {
		visitDataService.insertVisitData(request);
		param.setUserId(baseUtilCustom.getUserId());
		
		CommonResult result = articleService.likeOrHateThisChannel(param);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = ArticleUrlConstant.articleLongComplaint)
	public void articleLongComplaint(@RequestBody ArticleLongComplaintParam param, HttpServletRequest request, HttpServletResponse response) {
		visitDataService.insertVisitData(request);
		param.setComplaintUserId(baseUtilCustom.getUserId());
		CommonResult result = articleService.articleLongComplaint(param);
		outputJson(response, JSONObject.fromObject(result));
	}

}
