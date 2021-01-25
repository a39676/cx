package demo.finance.cryptoCoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;

@Controller
@RequestMapping( value = "/cryptoCoin")
public class CryptoCoinController extends CommonController {

	@Autowired
	private CryptoCoinPriceCacheService priceCacheService;
	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1MinDataService;
	
	@GetMapping(value = "/checkDataAPI")
	@ResponseBody
	public String checkDataAPI() {
		_1MinDataService.historyMQIsActive();
		priceCacheService.isSocketAlive();
		return "check";
	}
}
