package demo.article.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.article.article.pojo.constant.ArticleBurnUrlConstant;
import demo.article.article.pojo.constant.ArticleViewConstant;
import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import demo.article.article.service.ArticleBurnService;
import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping( value = ArticleBurnUrlConstant.root)
public class ArticleBurnController extends CommonController {

	@Autowired
	private ArticleBurnService articleBurnService;
	
	@GetMapping(value = ArticleBurnUrlConstant.createBurnMessage)
	public ModelAndView createBurnMessage() {
		ModelAndView view = new ModelAndView(ArticleViewConstant.createBurnMessage);
		return view;
	}
	
	@PostMapping(value = ArticleBurnUrlConstant.creatingBurnMessage)
	@ResponseBody
	public CreatingBurnMessageResult createBurnMessage(@RequestBody CreatingBurnMessageDTO dto) {
		return articleBurnService.creatingBurnMessage(dto);
	}
	
	@GetMapping(value = ArticleBurnUrlConstant.readBurningMessage)
	public ModelAndView readBurningMessage(@RequestParam(value = "readKey", defaultValue = "" ) String readKey) {
		return articleBurnService.readBurningMessage(readKey);
	}
	
	@GetMapping(value = ArticleBurnUrlConstant.burnMessage)
	public ModelAndView burnMessage(@RequestParam(value = "burnKey", defaultValue = "" ) String burnKey) {
		articleBurnService.burnArticleByBurnKey(burnKey);
		return new ModelAndView(ArticleViewConstant.burnMessage);
	}
	
}
