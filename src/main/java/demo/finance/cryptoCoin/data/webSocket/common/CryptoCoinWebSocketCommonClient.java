package demo.finance.cryptoCoin.data.webSocket.common;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;

public abstract class CryptoCoinWebSocketCommonClient extends CryptoCoinCommonService {

	@Autowired
	protected CryptoCoinPriceCacheService cacheService;
	
	protected Set<String> getSubscriptionList() {
		return constantService.getSubscriptionSet();
	}
	
	protected void addMainSubscription(String channel) {
		channel = channel.toUpperCase();
		constantService.getSubscriptionSet().add(channel);
	}

	protected void addMainSubscription(List<String> channelList) {
		for(String channel : channelList) {
			channel = channel.toUpperCase();
			constantService.getSubscriptionSet().add(channel);
		}
	}
	
	protected void removeMainSubscription(String channel) {
		channel = channel.toUpperCase();
		constantService.getSubscriptionSet().remove(channel);
	}
	
	protected void removeMainSubscription(List<String> channelList) {
		for(String channel : channelList) {
			channel = channel.toUpperCase();
			constantService.getSubscriptionSet().remove(channel);
		}
	}

	protected void removeAllMainSubscription() {
		constantService.getSubscriptionSet().clear();
	}
}
