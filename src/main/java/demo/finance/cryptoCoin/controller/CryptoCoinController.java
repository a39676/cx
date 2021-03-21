package demo.finance.cryptoCoin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;

@Controller
@RequestMapping( value = CryptoCoinPriceCommonUrl.ROOT)
public class CryptoCoinController extends CommonController {

	@Autowired
	private CryptoCoinPriceCacheService priceCacheService;
	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1MinDataService;
	@Autowired
	private CryptoCoinCatalogService catalogService;
	
	@GetMapping(value = "/checkDataAPI")
	@ResponseBody
	public String checkDataAPI() {
		_1MinDataService.historyMQIsActive();
		priceCacheService.isSocketAlive();
		return "check";
	}
	
	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_ALL_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getAllCatalog() {
		return catalogService.getAllCatalogVO();
	}
}
