package demo.article.article.controller;

import java.util.List;

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
import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.article.article.service.ArticleAdminService;
import demo.article.article.service.ArticleChannelService;
import demo.common.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	
	@PostMapping(value = ArticleAdminUrlConstant.passArticle)
	@ResponseBody
	public CommonResult passArticle(@RequestBody ReviewArticleLongParam param) {
		param.setReviewCode(ArticleReviewType.pass.getReviewCode());
		
		try {
			return articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			CommonResult serviceResult = new CommonResult();
			serviceResult.setMessage("Service error");
			return serviceResult;
		}
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.rejectArticle)
	@ResponseBody
	public CommonResult rejectArticle(@RequestBody ReviewArticleLongParam param) {
		param.setReviewCode(ArticleReviewType.reject.getReviewCode());
		
		try {
			return articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			CommonResult serviceResult = new CommonResult();
			serviceResult.setMessage("Service error");
			return serviceResult;
		}
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.deleteArticle)
	@ResponseBody
	public CommonResult deleteArticle(@RequestBody ReviewArticleLongParam param) {
		param.setReviewCode(ArticleReviewType.delete.getReviewCode());
		try {
			return articleAdminService.handelReviewArticle(param);
		} catch (Exception e) {
			CommonResult serviceResult = new CommonResult();
			serviceResult.setMessage("Service error");
			return serviceResult;
		}
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.changeChannel)
	@ResponseBody
	public CommonResult changeChannel(@RequestBody ChangeChannelParam param) throws Exception {
		return articleAdminService.changeChannel(param);
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.setArticleHot)
	@ResponseBody
	public CommonResult setArticleHot(@RequestBody SetArticleHotParam param, HttpServletRequest request, HttpServletResponse response) {
		return articleAdminService.setArticleHot(param);
	}
	
	@GetMapping(value = ArticleAdminUrlConstant.articleChannelManager)
	public ModelAndView articleChannelManager() {
		return channelService.articleChannelManagerView();
	}
	
	@PostMapping(value = ArticleAdminUrlConstant.articleChannelManager)
	@ResponseBody
	public CommonResult articleChannelManager(@RequestBody ArticleChannelManagerDTO dto) {
		return channelService.articleChannelManager(dto);
	}

	@PostMapping(value = ArticleAdminUrlConstant.editChannelKeyHostname)
	@ResponseBody
	public CommonResult editChannelKeyHostname(@RequestBody ArticleChannelKeyHostnameIdDTO dto) {
		return channelService.editChannelKeyHostname(dto);
	}
	
	@GetMapping(value = ArticleAdminUrlConstant.loadPublicChannels)
	@ResponseBody
	public CommonResult loadPublicChannels() {
		CommonResult r = new CommonResult();
		channelService.loadPublicChannels();
		r.setIsSuccess();
		return r;
	}
	
}
