package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;
import demo.interaction.wechat.service.impl.WechatCommonService;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.test.service.TestService2;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends WechatCommonService {

	@Autowired
	private TestService2 testService2;

	@GetMapping(value = "/t1")
	public ModelAndView testView() {
		return testService2.testView();
	}

	@PostMapping(value = "/t2")
	@ResponseBody
	public String t2(@RequestBody TestDTO dto) {
		return "{\"k\":\"v\"}";
	}
	
	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;

	@GetMapping(value = "/t3")
	@ResponseBody
	public String t3() {
		currencyExchangeRateNoticeService.noticeHandler();
		return "Done";
	}

}
