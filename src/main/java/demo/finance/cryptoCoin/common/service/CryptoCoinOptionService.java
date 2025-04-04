package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.finance.cryptoCoin.common.pojo.dto.CryptoCoinUserKeysCxDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinUserSymbolRateDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinForceOrderNoticeSettingDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolStreamDetailDTO;
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

	private boolean cryptoCompareWebSocketTurnOn = true;
	private boolean binanceWebSocketTurnOn = true;

	private List<String> binanceMainList = new ArrayList<>();
	private List<CryptoCoinSymbolStreamDetailDTO> binanceSymbolList;
	private Map<String, BigDecimal> binanceFutureUmSymbolBigStepMap = new HashMap<>();
	private BigDecimal bigTradeMinAmount = new BigDecimal(100000);
	private List<CryptoCoinForceOrderNoticeSettingDTO> forceOrderNoticeSetting;
	private List<CryptoCoinUserKeysCxDTO> userMetaData = new ArrayList<>();
	private Map<Integer, CryptoCoinUserKeysCxDTO> userMetaDataMap;
	private String ccmHost;
	private List<String> tradingSymbolList;
	private List<String> binanceFutureUmNotListing;
	private List<String> binanceFutureUmAlreadyWarningRemoved;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CRYPTO_COIN);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CRYPTO_COIN);
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
			CryptoCoinOptionService tmp = gson.fromJson(jsonStr, CryptoCoinOptionService.class);
			BeanUtils.copyProperties(tmp, this);

			BigDecimal step = null;
			for (CryptoCoinSymbolStreamDetailDTO symbolDetail : binanceSymbolList) {
				step = symbolDetail.getBigStep();
				if (step.compareTo(this.bigTradeMinAmount) < 0) {
					this.binanceFutureUmSymbolBigStepMap.put(symbolDetail.getSymbol(), this.bigTradeMinAmount);
				} else {
					this.binanceFutureUmSymbolBigStepMap.put(symbolDetail.getSymbol(), step);
				}
				if (symbolDetail.getScriptStreamGroupId() <= 1) {
					binanceMainList.add(symbolDetail.getSymbol());
				}
			}

			userMetaDataMap = new HashMap<Integer, CryptoCoinUserKeysCxDTO>();
			for (int i = 0; i < userMetaData.size(); i++) {
				CryptoCoinUserKeysCxDTO subUserMetaData = userMetaData.get(i);
				Map<String, CryptoCoinUserSymbolRateDTO> symbolRateMap = new HashMap<>();
				List<CryptoCoinUserSymbolRateDTO> rateSttingList = subUserMetaData.getSymbolRateSettingList();
				if (rateSttingList != null && !rateSttingList.isEmpty()) {
					for (int j = 0; j < rateSttingList.size(); j++) {
						CryptoCoinUserSymbolRateDTO symbolRate = rateSttingList.get(j);
						symbolRateMap.put(symbolRate.getSymbol(), symbolRate);
					}
				}
				subUserMetaData.setSymbolRateMap(symbolRateMap);
				userMetaDataMap.put(subUserMetaData.getLocalUserId(), subUserMetaData);
			}

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

	public List<String> getBinanceMainList() {
		return binanceMainList;
	}

	public void setBinanceMainList(List<String> binanceMainList) {
		this.binanceMainList = binanceMainList;
	}

	public List<CryptoCoinSymbolStreamDetailDTO> getBinanceSymbolList() {
		return binanceSymbolList;
	}

	public void setBinanceSymbolList(List<CryptoCoinSymbolStreamDetailDTO> binanceSymbolList) {
		this.binanceSymbolList = binanceSymbolList;
	}

	public Map<String, BigDecimal> getBinanceFutureUmSymbolBigStepMap() {
		return binanceFutureUmSymbolBigStepMap;
	}

	public void setBinanceFutureUmSymbolBigStepMap(Map<String, BigDecimal> binanceFutureUmSymbolBigStepMap) {
		this.binanceFutureUmSymbolBigStepMap = binanceFutureUmSymbolBigStepMap;
	}

	public BigDecimal getBigTradeMinAmount() {
		return bigTradeMinAmount;
	}

	public void setBigTradeMinAmount(BigDecimal bigTradeMinAmount) {
		this.bigTradeMinAmount = bigTradeMinAmount;
	}

	public List<CryptoCoinForceOrderNoticeSettingDTO> getForceOrderNoticeSetting() {
		return forceOrderNoticeSetting;
	}

	public void setForceOrderNoticeSetting(List<CryptoCoinForceOrderNoticeSettingDTO> forceOrderNoticeSetting) {
		this.forceOrderNoticeSetting = forceOrderNoticeSetting;
	}

	public List<CryptoCoinUserKeysCxDTO> getUserMetaData() {
		return userMetaData;
	}

	public void setUserMetaData(List<CryptoCoinUserKeysCxDTO> userMetaData) {
		this.userMetaData = userMetaData;
	}

	public String getCcmHost() {
		return ccmHost;
	}

	public void setCcmHost(String ccmHost) {
		this.ccmHost = ccmHost;
	}

	public List<String> getTradingSymbolList() {
		return tradingSymbolList;
	}

	public void setTradingSymbolList(List<String> tradingSymbolList) {
		this.tradingSymbolList = tradingSymbolList;
	}

	public Map<Integer, CryptoCoinUserKeysCxDTO> getUserMetaDataMap() {
		return userMetaDataMap;
	}

	public void setUserMetaDataMap(Map<Integer, CryptoCoinUserKeysCxDTO> userMetaDataMap) {
		this.userMetaDataMap = userMetaDataMap;
	}

	public List<String> getBinanceFutureUmNotListing() {
		return binanceFutureUmNotListing;
	}

	public void setBinanceFutureUmNotListing(List<String> binanceFutureUmNotListing) {
		this.binanceFutureUmNotListing = binanceFutureUmNotListing;
	}

	public List<String> getBinanceFutureUmAlreadyWarningRemoved() {
		return binanceFutureUmAlreadyWarningRemoved;
	}

	public void setBinanceFutureUmAlreadyWarningRemoved(List<String> binanceFutureUmAlreadyWarningRemoved) {
		this.binanceFutureUmAlreadyWarningRemoved = binanceFutureUmAlreadyWarningRemoved;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCoinCatalog=" + defaultCoinCatalog + ", cryptoCompareApiDataMaxLength="
				+ cryptoCompareApiDataMaxLength + ", defaultDailyDataQueryLenth=" + defaultDailyDataQueryLenth
				+ ", defaultCurrency=" + defaultCurrency + ", cryptoCompareApiKey=" + cryptoCompareApiKey
				+ ", cryptoCompareUri=" + cryptoCompareUri + ", binanceUri=" + binanceUri + ", scaleOfSharingCalculate="
				+ scaleOfSharingCalculate + ", sharingCalculateResultSavingPath=" + sharingCalculateResultSavingPath
				+ ", cryptoCompareWebSocketTurnOn=" + cryptoCompareWebSocketTurnOn + ", binanceWebSocketTurnOn="
				+ binanceWebSocketTurnOn + ", binanceMainList=" + binanceMainList + ", binanceSymbolList="
				+ binanceSymbolList + ", binanceFutureUmSymbolBigStepMap=" + binanceFutureUmSymbolBigStepMap
				+ ", bigTradeMinAmount=" + bigTradeMinAmount + ", forceOrderNoticeSetting=" + forceOrderNoticeSetting
				+ ", userMetaData=" + userMetaData + ", userMetaDataMap=" + userMetaDataMap + ", ccmHost=" + ccmHost
				+ ", tradingSymbolList=" + tradingSymbolList + "]";
	}

}
