package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.bo.CacheMapBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CryptoCoinConstantService extends CommonService {

	@Value("${optionFilePath.cryptoCoin}")
	private String optionFilePath;
	
	public static final String DEFAULT_COIN_CATALOG = "BTC";
	public static final Integer CRYPTO_COMPARE_API_DATA_MAX_LENGTH = 2000;
	
	private String defaultCurrency;

	private String cryptoCompareApiKey;
	private String cryptoCompareUri;
	private LocalDateTime cryptoCompareWebSocketLastActiveTime;

	private String binanceUri;
	private LocalDateTime binanceWebSocketLastActiveTime;

	private Set<String> subscriptionSet = new HashSet<>();
	private Set<String> lowPriceSubscriptionSet = new HashSet<>();

	private Map<CacheMapBO, CryptoCoinPriceCommonDataBO> cacheMap = new HashMap<>();
	
	private Map<String, List<LocalDateTime>> hitNoDataCountingMap = new HashMap<>();

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			CryptoCoinConstantService tmp = new Gson().fromJson(jsonStr, CryptoCoinConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("crypto coin constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String currency) {
		this.defaultCurrency = currency;
	}

	public String getCryptoCompareApiKey() {
		return cryptoCompareApiKey;
	}

	public void setCryptoCompareApiKey(String apiKey) {
		this.cryptoCompareApiKey = apiKey;
	}

	public String getCryptoCompareUri() {
		return cryptoCompareUri;
	}

	public void setCryptoCompareUri(String cryptoCompareUri) {
		this.cryptoCompareUri = cryptoCompareUri;
	}

	public LocalDateTime getCryptoCompareWebSocketLastActiveTime() {
		return cryptoCompareWebSocketLastActiveTime;
	}

	public void setCryptoCompareWebSocketLastActiveTime(LocalDateTime cryptoCompareWebSocketLastActiveTime) {
		this.cryptoCompareWebSocketLastActiveTime = cryptoCompareWebSocketLastActiveTime;
	}

	public String getBinanceUri() {
		return binanceUri;
	}

	public void setBinanceUri(String binanceUri) {
		this.binanceUri = binanceUri;
	}

	public LocalDateTime getBinanceWebSocketLastActiveTime() {
		return binanceWebSocketLastActiveTime;
	}

	public void setBinanceWebSocketLastActiveTime(LocalDateTime binanceWebSocketLastActiveTime) {
		this.binanceWebSocketLastActiveTime = binanceWebSocketLastActiveTime;
	}

	public Set<String> getSubscriptionSet() {
		return subscriptionSet;
	}

	public void setSubscriptionSet(Set<String> subscriptionList) {
		this.subscriptionSet = subscriptionList;
	}

	public Set<String> getLowPriceSubscriptionSet() {
		return lowPriceSubscriptionSet;
	}

	public void setLowPriceSubscriptionSet(Set<String> lowPriceSubscriptionSet) {
		this.lowPriceSubscriptionSet = lowPriceSubscriptionSet;
	}

	public Map<CacheMapBO, CryptoCoinPriceCommonDataBO> getCacheMap() {
		return cacheMap;
	}

	public void setCacheMap(Map<CacheMapBO, CryptoCoinPriceCommonDataBO> cacheMap) {
		this.cacheMap = cacheMap;
	}
	
	public Map<String, List<LocalDateTime>> getHitNoDataCountingMap() {
		return this.hitNoDataCountingMap;
	}
	
	public void putHitNoDataCoutingMap(String coinName) {
		if(hitNoDataCountingMap.containsKey(coinName)) {
			hitNoDataCountingMap.get(coinName).add(LocalDateTime.now());
		} else {
			hitNoDataCountingMap.put(coinName, Arrays.asList(LocalDateTime.now()));
		}
	}

	@Override
	public String toString() {
		return "CryptoCoinConstantService [optionFilePath=" + optionFilePath + ", defaultCurrency=" + defaultCurrency
				+ ", cryptoCompareApiKey=" + cryptoCompareApiKey + ", cryptoCompareUri=" + cryptoCompareUri
				+ ", cryptoCompareWebSocketLastActiveTime=" + cryptoCompareWebSocketLastActiveTime + ", binanceUri="
				+ binanceUri + ", binanceWebSocketLastActiveTime=" + binanceWebSocketLastActiveTime
				+ ", subscriptionSet=" + subscriptionSet + ", lowPriceSubscriptionSet=" + lowPriceSubscriptionSet
				+ ", cacheMap=" + cacheMap + "]";
	}

}