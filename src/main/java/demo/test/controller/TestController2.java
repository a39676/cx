package demo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;

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


}
