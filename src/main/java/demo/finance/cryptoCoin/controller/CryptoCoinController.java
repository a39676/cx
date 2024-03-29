package demo.finance.cryptoCoin.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import finance.cryptoCoin.pojo.constant.CryptoCoinPriceCommonUrl;
import finance.cryptoCoin.pojo.vo.CryptoCoinCatalogVO;

@Controller
@RequestMapping(value = CryptoCoinPriceCommonUrl.ROOT)
public class CryptoCoinController extends CommonController {

	@Autowired
	private CryptoCoinCatalogService catalogService;
	@Autowired
	private CryptoCoinLowPriceNoticeService lowPriceNoticeService;

	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_ALL_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getAllCatalog() {
		return catalogService.getAllCatalogVO();
	}

	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getSubscriptionCatalog() {
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();
		Set<CryptoCoinCatalogVO> voSet = new HashSet<>();

		voSet.addAll(catalogService.getSubscriptionCatalogVOList());
		voSet.addAll(lowPriceNoticeService.getLowPriceSubscriptionCatalogVOList());

		voList.addAll(voSet);

		return voList;
		
	}

	@GetMapping(value = CryptoCoinPriceCommonUrl.GET_LOW_PRICE_SUBSCRIPTION_CATALOG)
	@ResponseBody
	public List<CryptoCoinCatalogVO> getLowPriceSubscriptionCatalogVOList() {
		return lowPriceNoticeService.getLowPriceSubscriptionCatalogVOList();
	}

}
