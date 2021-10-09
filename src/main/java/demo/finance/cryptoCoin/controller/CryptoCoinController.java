package demo.finance.cryptoCoin.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;

@Controller
@RequestMapping(value = CryptoCoinPriceCommonUrl.ROOT)
public class CryptoCoinController extends CommonController {

	@Autowired
	private CryptoCoinCatalogService catalogService;
	@Autowired
	private CryptoCoinLowPriceNoticeService lowPriceNoticeService;
	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
	@Autowired
	protected CryptoCoinConstantService constantService;

	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_ALL_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getAllCatalog() {
		return catalogService.getAllCatalogVO();
	}

	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getSubscriptionCatalog() {
		return catalogService.getSubscriptionCatalogVOList();
	}
	
	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_LOW_PRICE_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getLowPriceSubscriptionCatalogVOList() {
		return lowPriceNoticeService.getLowPriceSubscriptionCatalogVOList();
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
