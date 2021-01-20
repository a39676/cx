package demo.test.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.webSocket.CryptoCompareWSClient;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	
	@Autowired
	private CryptoCompareWSClient cryptoCompareWSClient;
	
	@GetMapping(value = "/ws")
	@ResponseBody
	public String start() {
		if(cryptoCompareWSClient.getSocketLiveFlag()) {
			return "working";
		} else {
			cryptoCompareWSClient.startWebSocket();
			return "restart";
		}
	}
	
	@Autowired
	private CryptoCoinPriceCacheService cryptoCoinPriceCacheService;
	
	@GetMapping(value = "/getCache")
	@ResponseBody
	public String getCache(@RequestParam("coinType")String coinType, @RequestParam("currencyType")String currencyType) {
		return String.valueOf(cryptoCoinPriceCacheService.getCommonData(CryptoCoinType.getType(coinType), CurrencyType.getType(currencyType)));
	}
	
	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1MinuteDataSummaryService;
	
	@GetMapping(value = "/get1Min")
	@ResponseBody
	public String get1Min(@RequestParam("coinType")String coinType, @RequestParam("currencyType")String currencyType) {
		return String.valueOf(_1MinuteDataSummaryService.getCommonDataFillWithCache(
				CryptoCoinType.getType(coinType), 
				CurrencyType.getType(currencyType), 
				LocalDateTime.now().minusMinutes(10))
				);
	}
	
	@Autowired
	private CryptoCoin5MinuteDataSummaryService _5MinuteDataSummaryService;
	
	@GetMapping(value = "/get5Min")
	@ResponseBody
	public String get5Min(@RequestParam("coinType")String coinType, @RequestParam("currencyType")String currencyType) {
		return String.valueOf(_5MinuteDataSummaryService.getCommonDataFillWithCache(
				CryptoCoinType.getType(coinType), 
				CurrencyType.getType(currencyType), 
				LocalDateTime.now().minusMinutes(10))
				);
	}
}
