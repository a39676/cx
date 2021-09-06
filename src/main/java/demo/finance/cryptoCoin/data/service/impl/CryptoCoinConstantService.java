package demo.finance.cryptoCoin.data.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CryptoCoinConstantService extends CommonService {

	@Value("${optionFilePath.cryptoCoin}")
	private String optionFilePath;

	private String defaultCurrency;

	private String cryptoCompareApiKey;
	private String cryptoCompareUri;
	private LocalDateTime cryptoCompareWebSocketLastActiveTime;

	private String binanceUri;
	private LocalDateTime binanceWebSocketLastActiveTime;

	private Set<String> subscriptionSet = new HashSet<>();

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

	@Override
	public String toString() {
		return "CryptoCoinConstantService [optionFilePath=" + optionFilePath + ", defaultCurrency=" + defaultCurrency
				+ ", cryptoCompareApiKey=" + cryptoCompareApiKey + ", cryptoCompareUri=" + cryptoCompareUri
				+ ", cryptoCompareWebSocketLastActiveTime=" + cryptoCompareWebSocketLastActiveTime + ", binanceUri="
				+ binanceUri + ", binanceWebSocketLastActiveTime=" + binanceWebSocketLastActiveTime
				+ ", subscriptionSet=" + subscriptionSet + "]";
	}

}
