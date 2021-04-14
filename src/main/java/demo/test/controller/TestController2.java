package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
	@Autowired
	private CryptoCoinLowPriceNoticeService cryptoCoinLowPriceNoticeService;
	
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		cryptoCoin1DayDataSummaryService.setWaitingUpdateCoinType();
		cryptoCoin1DayDataSummaryService.sendCryptoCoinDailyDataQueryMsg();
		return "done";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public String setLowPriceCoinWatching() {
		cryptoCoinLowPriceNoticeService.setNewLowPriceSubscription();
		cryptoCoinLowPriceNoticeService.setLowPriceCoinWatching();
		return "done";
	}
}
