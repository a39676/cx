package demo.finance.cryptoCoin.data.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinManagerUrl;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.data.webSocket.BinanceWSClient;
import demo.finance.cryptoCoin.data.webSocket.CryptoCompareWSClient;

@Controller
@RequestMapping(value = CryptoCoinManagerUrl.ROOT)
public class CryptoCoinManagerController extends CommonController {

	@Autowired
	private CryptoCoinPriceCacheService priceCacheService;
	@Autowired
	private CryptoCoinCatalogService catalogService;
//	@Autowired
//	private CryptoCoin1DayDataSummaryService dailyDataService;
	@Autowired
	private CryptoCompareWSClient cryptoCompareWSClient;
	@Autowired
	private BinanceWSClient binanceWSClient;
	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
	@Autowired
	private CryptoCoinConstantService constantService;

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

	@GetMapping(value = CryptoCoinManagerUrl.CRYPTO_COIN_WEB_SOCKET_MANAGER)
	public ModelAndView cryptoCoinWebSocketManager() {
		return new ModelAndView("finance/cryptoCoin/CryptoCoinWebSocketManager");
	}

	@GetMapping(value = CryptoCoinManagerUrl.RESTART_COIN_COMPARE_WEB_SOCKET)
	@ResponseBody
	public String restartCoinCompareWS() {
		cryptoCompareWSClient.restart();
		return "done";
	}

	@GetMapping(value = CryptoCoinManagerUrl.RESTART_BINANCE_WEB_SOCKET)
	@ResponseBody
	public String restartBinanceWS() {
		binanceWSClient.restartWebSocket();
		return "done";
	}

	@GetMapping(value = "/resetDailyDataWaitingQuerySet")
	@ResponseBody
	public String resetDailyDataWaitingQuerySet() {
		cryptoCoin1DayDataSummaryService.resetDailyDataWaitingQuerySet();
		return "done";
	}

	@GetMapping(value = "/sendAllCryptoCoinDailyDataQueryMsg")
	@ResponseBody
	public String sendAllCryptoCoinDailyDataQueryMsg() {
		cryptoCoin1DayDataSummaryService.sendAllCryptoCoinDailyDataQueryMsg();
		return "done";
	}

	@GetMapping(value = "/getDailyDataWaitingQuerySet")
	@ResponseBody
	public Set<String> getDailyDataWaitingQuerySet() {
		return constantService.getDailyDataWaitingQuerySet();
	}

}
