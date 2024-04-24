package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCoinOptionService extends CommonService {

	private String defaultCoinCatalog;
	private Integer cryptoCompareApiDataMaxLength = 2000;
	private Integer defaultDailyDataQueryLenth = 5;

	private String defaultCurrency;

	private String cryptoCompareApiKey;
	private String cryptoCompareUri;

	private String binanceUri;

	private Integer scaleOfSharingCalculate = 4;
	private String sharingCalculateResultSavingPath;

	private Set<String> subscriptionSet = new HashSet<>();

	private boolean cryptoCompareWebSocketTurnOn = true;
	private boolean binanceWebSocketTurnOn = true;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CRYPTO_COIN);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CRYPTO_COIN);
			CryptoCoinOptionService tmp = new Gson().fromJson(jsonStr, CryptoCoinOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("crypto coin option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
	}

	public String getDefaultCoinCatalog() {
		return defaultCoinCatalog;
	}

	public void setDefaultCoinCatalog(String defaultCoinCatalog) {
		this.defaultCoinCatalog = defaultCoinCatalog;
	}

	public Integer getCryptoCompareApiDataMaxLength() {
		return cryptoCompareApiDataMaxLength;
	}

	public void setCryptoCompareApiDataMaxLength(Integer cryptoCompareApiDataMaxLength) {
		this.cryptoCompareApiDataMaxLength = cryptoCompareApiDataMaxLength;
	}

	public Integer getDefaultDailyDataQueryLenth() {
		return defaultDailyDataQueryLenth;
	}

	public void setDefaultDailyDataQueryLenth(Integer defaultDailyDataQueryLenth) {
		this.defaultDailyDataQueryLenth = defaultDailyDataQueryLenth;
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

	public String getBinanceUri() {
		return binanceUri;
	}

	public void setBinanceUri(String binanceUri) {
		this.binanceUri = binanceUri;
	}

	public Set<String> getSubscriptionSet() {
		return subscriptionSet;
	}

	public void setSubscriptionSet(Set<String> subscriptionList) {
		this.subscriptionSet = subscriptionList;
	}

	public Integer getScaleOfSharingCalculate() {
		return scaleOfSharingCalculate;
	}

	public void setScaleOfSharingCalculate(Integer scaleOfSharingCalculate) {
		this.scaleOfSharingCalculate = scaleOfSharingCalculate;
	}

	public String getSharingCalculateResultSavingPath() {
		return sharingCalculateResultSavingPath;
	}

	public void setSharingCalculateResultSavingPath(String sharingCalculateResultSavingPath) {
		this.sharingCalculateResultSavingPath = sharingCalculateResultSavingPath;
	}

	public boolean getCryptoCompareWebSocketTurnOn() {
		return cryptoCompareWebSocketTurnOn;
	}

	public void setCryptoCompareWebSocketTurnOn(boolean cryptoCompareWebSocketTurnOn) {
		this.cryptoCompareWebSocketTurnOn = cryptoCompareWebSocketTurnOn;
	}

	public boolean getBinanceWebSocketTurnOn() {
		return binanceWebSocketTurnOn;
	}

	public void setBinanceWebSocketTurnOn(boolean binanceWebSocketTurnOn) {
		this.binanceWebSocketTurnOn = binanceWebSocketTurnOn;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCoinCatalog=" + defaultCoinCatalog + ", cryptoCompareApiDataMaxLength="
				+ cryptoCompareApiDataMaxLength + ", defaultDailyDataQueryLenth=" + defaultDailyDataQueryLenth
				+ ", defaultCurrency=" + defaultCurrency + ", cryptoCompareApiKey=" + cryptoCompareApiKey
				+ ", cryptoCompareUri=" + cryptoCompareUri + ", binanceUri=" + binanceUri + ", scaleOfSharingCalculate="
				+ scaleOfSharingCalculate + ", sharingCalculateResultSavingPath=" + sharingCalculateResultSavingPath
				+ ", subscriptionSet=" + subscriptionSet + ", cryptoCompareWebSocketTurnOn="
				+ cryptoCompareWebSocketTurnOn + ", binanceWebSocketTurnOn=" + binanceWebSocketTurnOn + "]";
	}

}
