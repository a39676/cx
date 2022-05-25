package demo.finance.cryptoCoin.data.webSocket.common;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;

public abstract class CryptoCoinWebSocketCommonClient extends CryptoCoinCommonService {

	@Autowired
	protected CryptoCoinPriceCacheService cacheService;
	
	@Autowired
	protected CryptoCoinCatalogService catalogService;
	
	protected Set<String> getSubscriptionList() {
		return catalogService.getSubscriptionNameList();
	}
	
	protected void addMainSubscription(String channel) {
		catalogService.addSubscriptionCatalog(channel);
	}

	protected void addMainSubscription(List<String> channelList) {
		catalogService.addSubscriptionCatalog(channelList);
	}
	
	protected void removeMainSubscription(String channel) {
		catalogService.removeSubscriptionCatalog(channel);
	}
	
	protected void removeMainSubscription(List<String> channelList) {
		catalogService.removeSubscriptionCatalog(channelList);
	}

	protected void removeAllMainSubscription() {
		catalogService.removeAllSubscriptionCatalog();
	}
}
