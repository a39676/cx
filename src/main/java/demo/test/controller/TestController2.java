package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import demo.test.pojo.constant.TestUrl;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

////	@SuppressWarnings("unused")
//	@Autowired
//	private TestService testService;
//	
//	@GetMapping(value = "/get5MinData")
//	@ResponseBody
//	public String get5MinData() {
//		return testService.get5MinData();
//	}
//	
//	@GetMapping(value = "/createCacheData")
//	@ResponseBody
//	public String createCacheData() {
//		testService.createCacheData();
//		return "done";
//	}
	
//	@Autowired
//	private CryptoCoin1MinuteDataSummaryService _1MinuteDataService;
//	
//	@GetMapping(value = "/getNewData")
//	@ResponseBody
//	public String getNewData() {
//		List<CryptoCoinPriceCommonDataBO> dataList = _1MinuteDataService.getCommonDataFillWithCache(CryptoCoinType.BTC, CurrencyType.USD, LocalDateTime.now().minusMinutes(2));
//		for(CryptoCoinPriceCommonDataBO bo : dataList) {
//			System.out.println(bo.toString());
//		}
//		return "done";
//	}

	@Autowired
	private CryptoCoinDailyDataQueryAckProducer producer;
	
	@GetMapping("/test")
	@ResponseBody
	public String test(@RequestParam("coin") String coinName, @RequestParam("counting") Integer counting) {
		CryptoCoinDailyDataQueryDTO dto = new CryptoCoinDailyDataQueryDTO();
		dto.setCoinName(coinName);
		dto.setCounting(counting);
		producer.send(dto);
		return "done";
	}
}
