package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

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
	
	@SuppressWarnings("unused")
	@Autowired
	private TestService testservice;
	
}
