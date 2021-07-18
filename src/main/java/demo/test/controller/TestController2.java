package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.article.article.service.impl.ArticleConstantService;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@Autowired
	private CryptoCoinLowPriceNoticeService cryptoCoinLowPriceNoticeService;
	
	@GetMapping(value ="/test")
	@ResponseBody
	public String test() {
		cryptoCoinLowPriceNoticeService.setNewLowPriceSubscription();
		cryptoCoinLowPriceNoticeService.setLowPriceCoinWatching();
		return "done";
	}
	
	@Autowired
	private ArticleConstantService acs;
	
	@GetMapping(value ="/test2")
	@ResponseBody
	public String test2() {
		acs.testing();
		return "done";
	}
}
