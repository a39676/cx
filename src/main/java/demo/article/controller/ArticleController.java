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
import demo.article.pojo.dto.ArticleFeedbackDTO;
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
import demo.tool.service.VisitDataService;
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
	private VisitDataService visitDataService;
	
//	@Autowired
//	private BaseUtilCustom baseUtilCustom;
	
	@GetMapping(value = ArticleUrlConstant.createBurnMessage)
	public ModelAndView createBurnMessage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.createBurnMessage);
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.creatingBurnMessage)
	public void createBurnMessage(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		CreatingBurnMessageResult serviceResult = articleBurnService.creatingBurnMessage(jsonInput);
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
		CreatingArticleParam param = new CreatingArticleParam();
		ModelAndView view = articleService.creatingArticleLong(param);
		GetArticleChannelsResult channelsResult = getArticleChannelsDynamic(request);
		view.addObject("channelList", channelsResult.getChannelList());
		
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.findChannels)
	@ResponseBody
	public GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request) {
		GetArticleChannelsResult result = channelService.getArticleChannelsDynamic(request);
		return result;
	}
	
//	private GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request) {
//		Long userId = baseUtilCustom.getUserId();
//		return channelService.getArticleChannelsDynamic(request, userId);
//	}
	
	@PostMapping(value = ArticleUrlConstant.articleLongSummaryListByChannel)
	public void articleLongSummaryListByChannel(@RequestBody FindArticleLongSummaryListControllerParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FindArticleLongSummaryListResultV3 result = null;
		if(param.getIsHot()) {
			result = summaryService.articleLongSummaryHotListByChannelIdV3(param, request);
		} else {
			result = summaryService.articleLongSummaryListByChannelIdV3(param, request);
		}
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@GetMapping(value = ArticleUrlConstant.readArticleLong)
	public ModelAndView readArticleLong(@RequestParam(value = "", required = false) String pk, HttpServletRequest request) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readArticleLongCleanBlog);
		FindArticleLongByArticleSummaryPrivateKeyParam param = new FindArticleLongByArticleSummaryPrivateKeyParam();
		param.setPrivateKey(pk);
		
		FindArticleLongResult result = articleService.findArticleLongByArticleSummaryPrivateKey(param, request);
		view.addObject("articleLongVO", result.getArticleLongVO());
		view.addObject("visitCount", visitDataService.getVisitCount());
		view.addObject("title", result.getArticleLongVO().getArticleTitle());
		return view;
	}
	
	@PostMapping(value = ArticleUrlConstant.deleteArticle)
	public void deleteArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request, HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());
		
		CommonResultCX serviceResult = new CommonResultCX();

		if(!articleService.iWroteThis(param.getPk())) {
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
		CommonResult result = articleService.likeOrHateThisChannel(param, request);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = ArticleUrlConstant.articleLongFeedback)
	@ResponseBody
	public CommonResultCX articleLongFeedback(@RequestBody ArticleFeedbackDTO dto, HttpServletRequest request) {
		CommonResultCX result = articleService.articleLongFeedback(dto, request);
		return result;
	}

}
