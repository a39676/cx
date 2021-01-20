package demo.finance.cryptoCoin.webSocket;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.base.system.service.GlobalOptionService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCompareSocketConfigBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCompareConstant;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCompareWSClient extends CommonService {

	@Autowired
	private GlobalOptionService globalOptionService;
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;

	public CommonResult startWebSocket() {
		CryptoCompareSocketConfigBO configBO = getConfig();
		CommonResult r = new CommonResult();
		if (configBO == null) {
			r.failWithMessage("load config error");
			return r;
		}

		WebSocket ws = createWebSocket(configBO);
		if (ws == null) {
			r.failWithMessage("create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
			addSubscription(ws, configBO);
			setSocketLiveFlag("1");
			r.normalSuccess();
		} catch (WebSocketException e) {
			log.error("crypto compare socket connect error: " + e.getLocalizedMessage());
		}

		return r;
	}

	private WebSocket createWebSocket(CryptoCompareSocketConfigBO configBO) {
		String uriStr = configBO.getUri() + "?api_key=" + configBO.getApiKey();
		try {
			WebSocket ws = new WebSocketFactory().createSocket(uriStr);
			return ws;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("create socket error: " + e.getLocalizedMessage());
			return null;
		}
	}

	private WebSocket setListener(WebSocket ws) {
		ws.addListener(new WebSocketAdapter() {
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
				refreshLastActiveTime();
				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
					cacheService.reciveData(dataBO);
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
				setSocketLiveFlag("0");
			}
		});
		return ws;
	}

	private void addSubscription(WebSocket ws, CryptoCompareSocketConfigBO bo) {
		JSONObject json = new JSONObject();
		json.put("action", "SubAdd");
		JSONArray subs = new JSONArray();
		String subscriptionformat = "5~CCCAGG~%s~%s";
		String subscription = null;
		CryptoCoinType coinType = null;
		CurrencyType currencyType = null;
		for (String cryptoCoinCode : bo.getTargetCoins()) {
			coinType = CryptoCoinType.getType(cryptoCoinCode);
			if (coinType == null) {
				continue;
			}
			for (String currencyCode : bo.getTargetCurrency()) {
				currencyType = CurrencyType.getType(currencyCode);
				if (currencyType == null) {
					continue;
				}
				subscription = String.format(subscriptionformat, coinType.getName(), currencyType.getName());
				subs.add(subscription);
			}
		}
		json.put("subs", subs);

		ws.sendText(json.toString());
	}

	/**
	 * 
	 * @param intFlag 1 = active, 0 = inactive
	 */
	public void setSocketLiveFlag(String intFlag) {
		constantService.setValByName(CryptoCompareConstant.SOCKET_LIVE_FLAG_REDIS_KEY, intFlag);
	}

	public boolean getSocketLiveFlag() {
		String str = constantService.getValByName(CryptoCompareConstant.SOCKET_LIVE_FLAG_REDIS_KEY);
		if(!"1".equals(str)) {
			return false;
		}
//		if (!"1".equals(constantService.getValByName(CryptoCompareConstant.SOCKET_LIVE_FLAG_REDIS_KEY))) {
//			return false;
//		}

		String lastActiveTimeStr = constantService
				.getSysValByName(CryptoCompareConstant.SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
		LocalDateTime lastActiveTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(lastActiveTimeStr);
		boolean flag = lastActiveTime.plusSeconds(CryptoCompareConstant.SOCKET_INACTIVE_JUDGMENT_SECOND)
				.isAfter(LocalDateTime.now());
		if (!flag) {
			setSocketLiveFlag("0");
		}
		return flag;
	}

	private void refreshLastActiveTime() {
		constantService.setValByName(CryptoCompareConstant.SOCKET_LAST_ACTIVE_TIME_REDIS_KEY,
				localDateTimeHandler.dateToStr(LocalDateTime.now()));
	}

	private CryptoCompareSocketConfigBO getConfig() {
		CryptoCompareSocketConfigBO bo = null;
		String systemParameterSavingFolderPath = globalOptionService.getParameterSavingFolder();
		String cryptoCompareParameterSavingFolderPath = systemParameterSavingFolderPath + File.separator
				+ CryptoCompareConstant.PARAM_STORE_PATH;

		File configFile = new File(cryptoCompareParameterSavingFolderPath);

		if (!configFile.exists() || !configFile.isFile()) {
			log.error("crypto compare config file not exists");
			return bo;
		}

		String jsonStr = ioUtil.getStringFromFile(cryptoCompareParameterSavingFolderPath);
		if (StringUtils.isBlank(jsonStr)) {
			log.error("crypto compare config file format error");
			return bo;
		}

		try {
			bo = new Gson().fromJson(jsonStr, CryptoCompareSocketConfigBO.class);
		} catch (Exception e) {
			log.error("crypto compare config trans error");
			return bo;
		}

		return bo;
	}

	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String msg) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject j = JSONObject.fromObject(msg);
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setEndPrice(new BigDecimal(j.getDouble("PRICE")));
			bo.setCoinType(CryptoCoinType.getType(j.getString("FROMSYMBOL")).getCode());
			bo.setCurrencyType(CurrencyType.getType(j.getString("TOSYMBOL")).getCode());
			try {
				Date tradDate = new Date(j.getLong("LASTUPDATE") * 1000);
				bo.setStartTime(localDateTimeHandler.dateToLocalDateTime(tradDate));
			} catch (Exception e) {
				bo.setStartTime(LocalDateTime.now());
			}
		} catch (Exception e) {
		}
		return bo;
	}

}
