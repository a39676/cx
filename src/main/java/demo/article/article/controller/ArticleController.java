package demo.article.article.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
import demo.article.article.pojo.constant.ArticleUrlConstant;
import demo.article.article.pojo.dto.EditArticleLongDTO;
import demo.article.article.pojo.dto.FindArticleLongSummaryListDTO;
import demo.article.article.pojo.dto.ReadyToEditArticleLongDTO;
import demo.article.article.pojo.param.controllerParam.CreateArticleParam;
import demo.article.article.pojo.param.controllerParam.CreatingArticleParam;
import demo.article.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.result.jsonRespon.FindArticleLongSummaryListResult;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.service.ArticleChannelService;
import demo.article.article.service.ArticleEvaluationService;
import demo.article.article.service.ArticleService;
import demo.article.article.service.ArticleSummaryService;
import demo.common.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = ArticleUrlConstant.root)
public class ArticleController extends CommonController {

	@Autowired
	private ArticleAdminController articleAdminController;

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleEvaluationService articleEvaluationService;
	@Autowired
	private ArticleChannelService channelService;
	@Autowired
	private ArticleSummaryService summaryService;

	@PostMapping(value = ArticleUrlConstant.createArticleLong)
	@ResponseBody
	public CommonResult createArticleLong(@RequestBody CreateArticleParam dto)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		CommonResult serviceResult = articleService.crateArticleLongPrefixServcie(dto);
		return serviceResult;
	}

	@GetMapping(value = ArticleUrlConstant.creatingArticleLong)
	public ModelAndView creatingArticleLong(HttpServletRequest request) {
		CreatingArticleParam param = new CreatingArticleParam();
		ModelAndView view = articleService.buildCreatingArticleLongView(param);
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

	@PostMapping(value = ArticleUrlConstant.articleLongSummaryListByChannel)
	@ResponseBody
	public FindArticleLongSummaryListResult articleLongSummaryListByChannel(
			@RequestBody FindArticleLongSummaryListDTO param, HttpServletRequest request) {
		if (param.getArticleChannelId() == null) {
			return summaryService.summaryListWithoutChannel(param, request);
		} else {
			return summaryService.summaryListByChannelId(param, request);
		}

	}

	@GetMapping(value = ArticleUrlConstant.readArticleLong)
	public ModelAndView readArticleLong(@RequestParam(value = "pk", required = false) String pk,
			HttpServletRequest request) {
		return articleService.readArticleLong(pk, request);
	}

	@PostMapping(value = ArticleUrlConstant.deleteArticle)
	@ResponseBody
	public CommonResult deleteArticle(@RequestBody ReviewArticleLongParam param, HttpServletRequest request,
			HttpServletResponse response) {
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());

		CommonResult serviceResult = new CommonResult();

		if (!articleService.iWroteThis(param.getPk())) {
			serviceResult.setMessage("无权限编辑");
			return serviceResult;
		}

		return articleAdminController.deleteArticle(param);
	}

	@PostMapping(value = ArticleUrlConstant.insertArticleLongEvaluation)
	@ResponseBody
	public CommonResult insertArticleLongEvaluation(@RequestBody InsertArticleLongEvaluationParam param) {
		param.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		return articleEvaluationService.insertArticleLongEvaluationRedis(param);
	}

	public CommonResult insertArticleLongCommentEvaluation(@RequestBody InsertArticleLongEvaluationParam param) {
		/*
		 * TODO 2019-06-08 发现 未明用途 2023-03-25 准备删除
		 */
		param.setEvaluationType(ArticleEvaluationType.articleCommentEvaluation.getCode());
		CommonResult serviceResult = articleEvaluationService.insertArticleLongEvaluationRedis(param);
		return serviceResult;
	}

	@GetMapping(value = ArticleUrlConstant.editArticleLong)
	public ModelAndView editArticleLongView(@RequestParam(value = "pk", required = false) String pk,
			HttpServletRequest request) {
		ReadyToEditArticleLongDTO dto = new ReadyToEditArticleLongDTO();
		dto.setPrivateKey(pk);
		ModelAndView view = articleService.readyToEditArticleLong(dto);
		GetArticleChannelsResult channelsResult = getArticleChannelsDynamic(request);
		view.addObject("channelList", channelsResult.getChannelList());
		return view;
	}

	@PostMapping(value = ArticleUrlConstant.editArticleLong)
	@ResponseBody
	public CommonResult editArticleLong(@RequestBody EditArticleLongDTO dto)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, IOException {
		return articleService.editArticleLongHandler(dto);
	}

}
