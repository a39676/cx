package demo.finance.cryptoCoin.common.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import finance.cryptoCoin.pojo.bo.CryptoCoinDataCacheMapKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Scope("singleton")
@Service
public class CryptoCoinConstantService extends CommonService {

	private Set<String> lowPriceSubscriptionSet = new HashSet<>();
	private Set<String> dailyDataWaitingQuerySet = new HashSet<>();

	private Map<CryptoCoinDataCacheMapKeyBO, CryptoCoinPriceCommonDataBO> cacheMap = new HashMap<>();

	private Map<String, List<LocalDateTime>> hitNoDataCountingMap = new HashMap<>();

	public Set<String> getLowPriceSubscriptionSet() {
		return lowPriceSubscriptionSet;
	}

	public void setLowPriceSubscriptionSet(Set<String> lowPriceSubscriptionSet) {
		this.lowPriceSubscriptionSet = lowPriceSubscriptionSet;
	}

	public Set<String> getDailyDataWaitingQuerySet() {
		return dailyDataWaitingQuerySet;
	}

	public void setDailyDataWaitingQuerySet(Set<String> dailyDataWaitingQuerySet) {
		this.dailyDataWaitingQuerySet = dailyDataWaitingQuerySet;
	}

	public Map<CryptoCoinDataCacheMapKeyBO, CryptoCoinPriceCommonDataBO> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(Map<CryptoCoinDataCacheMapKeyBO, CryptoCoinPriceCommonDataBO> cacheMap) {
		this.cacheMap = cacheMap;
	}

	public Map<String, List<LocalDateTime>> getHitNoDataCountingMap() {
		return hitNoDataCountingMap;
	}

	public void setHitNoDataCountingMap(Map<String, List<LocalDateTime>> hitNoDataCountingMap) {
		this.hitNoDataCountingMap = hitNoDataCountingMap;
	}

	@Override
	public String toString() {
		return "CryptoCoinConstantService [lowPriceSubscriptionSet=" + lowPriceSubscriptionSet
				+ ", dailyDataWaitingQuerySet=" + dailyDataWaitingQuerySet + ", cacheMap=" + cacheMap
				+ ", hitNoDataCountingMap=" + hitNoDataCountingMap + "]";
	}

}
