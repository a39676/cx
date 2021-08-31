package demo.finance.cryptoCoin.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinManagerUrl;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;

@Controller
@RequestMapping(value = CryptoCoinManagerUrl.ROOT)
public class CryptoCoinManagerController extends CommonController {

	@Autowired
	private CryptoCoinPriceCacheService priceCacheService;
	@Autowired
	private CryptoCoinCatalogService catalogService;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;

	@GetMapping(value = CryptoCoinManagerUrl.CHECK_DATA_API)
	@ResponseBody
	public String checkDataAPI() {
		priceCacheService.isSocketAlive();
		return "check";
	}

	@GetMapping(value = CryptoCoinManagerUrl.ADD_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public String addSubscriptionCatalog(@RequestParam("coinName") String coinName) {
		catalogService.addSubscriptionCatalog(coinName);
		return "done";
	}

	@GetMapping(value = CryptoCoinManagerUrl.REMOVE_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public String removeSubscriptionCatalog(@RequestParam("coinName") String coinName) {
		catalogService.removeSubscriptionCatalog(coinName);
		return "done";
	}

	@GetMapping(value = CryptoCoinManagerUrl.REMOVE_ALL_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public String removeAllSubscriptionCatalog() {
		catalogService.removeAllSubscriptionCatalog();
		return "done";
	}

	@GetMapping(value = CryptoCoinManagerUrl.SEND_CRYPTO_COIN_DAILY_DATA_QUERY_MSG)
	@ResponseBody
	public String sendCryptoCoinDailyDataQueryMsg(@RequestParam("coinName") String coinName,
			@RequestParam(value = "currencyName",  required=false) String currencyName, 
			@RequestParam("counting") Integer counting) {
		dailyDataService.sendCryptoCoinDailyDataQueryMsg(coinName, currencyName, counting);
		return "done";
	}
}
