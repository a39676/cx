package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;

@Component
public class CryptoCoinCatalogTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CryptoCoinCatalogService catalogService;

	@Scheduled(fixedRate = 1000L * 60)
	public void cryptoCoinPriceNoticeHandler() {
		catalogService.addCatalog("BTC");
	}
	
}
