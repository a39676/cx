package demo.finance.cryptoCoin.data.webSocket;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCompareSocketConfigBO;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCompareWebSocketMsgType;
import demo.finance.cryptoCoin.data.webSocket.common.CryptoCoinWebSocketCommonClient;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CryptoCompareWSClient extends CryptoCoinWebSocketCommonClient {

	private WebSocket ws = null;

	@Autowired
	private FileUtilCustom ioUtil;

	public WebSocket getWs() {
		if (ws == null) {
			createWebSocket(getDefaultConfig());
		}
		return ws;
	}

	private CryptoCompareSocketConfigBO getDefaultConfig() {
		CryptoCompareSocketConfigBO bo = null;
		// 迁移代码 此处为获取参数文件路径
//		String systemParameterSavingFolderPath = globalOptionService.getParameterSavingFolder();
		String systemParameterSavingFolderPath = "";
		String cryptoCompareParameterSavingFolderPath = systemParameterSavingFolderPath + File.separator
				+ CryptoCoinWebSocketConstant.CRYPTO_COMPARE_PARAM_STORE_PATH;

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

	private CryptoCompareWebSocketMsgType checkConnection(String msg) {
		/*
		 * example:
		 * {"TYPE":"500","MESSAGE":"INVALID_SUB","PARAMETER":"5~CCCAGG~NULL~USD",
		 * "INFO":"We have not integrated any of the exchanges NULL trades on or we have not currently mapped it."
		 * }
		 */
		try {
			JSONObject j = JSONObject.fromObject(msg);
			Integer typeCode = j.getInt("TYPE");
			if (typeCode == 500) {
				return CryptoCompareWebSocketMsgType.getType(j.getString("MESSAGE"));
			} else {
				return CryptoCompareWebSocketMsgType.getType(typeCode);
			}
		} catch (Exception e) {
			return null;
		}
	}

	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String sourceMsg) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject sourceMsgJson = JSONObject.fromObject(sourceMsg);
			if (!sourceMsgJson.containsKey("PRICE")) {
				return null;
			}

			bo = new CryptoCoinPriceCommonDataBO();
			bo.setStartPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setEndPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setHighPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setLowPrice(new BigDecimal(sourceMsgJson.getDouble("PRICE")));
			bo.setCoinType(sourceMsgJson.getString("FROMSYMBOL"));
			CurrencyType currencyType = CurrencyType.getType(sourceMsgJson.getString("TOSYMBOL"));
			/* TODO 2021-04-08 临时处理, usdt 等同 usd 处理 */
			if (CurrencyType.USDT.equals(currencyType)) {
				currencyType = CurrencyType.USD;
			}
			bo.setCurrencyType(currencyType.getCode());
			try {
				Date tradDate = new Date(sourceMsgJson.getLong("LASTUPDATE") * 1000);
				LocalDateTime tradDateTime = localDateTimeHandler.dateToLocalDateTime(tradDate);
				if (tradDateTime == null) {
					tradDateTime = LocalDateTime.now();
				}
				bo.setStartTime(tradDateTime);
				bo.setEndTime(tradDateTime);
			} catch (Exception e) {
				bo.setStartTime(LocalDateTime.now());
				bo.setEndTime(LocalDateTime.now());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bo;
	}

	private WebSocket createWebSocket(CryptoCompareSocketConfigBO configBO) {
		String uriStr = configBO.getUri() + "?api_key=" + configBO.getApiKey();
		try {
			WebSocket ws = new WebSocketFactory().setVerifyHostname(false).createSocket(uriStr);
			return ws;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("crypto comapre create socket error: " + e);
			return null;
		}
	}

	private WebSocket setListener(WebSocket ws) {
		ws.addListener(new WebSocketAdapter() {
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
//				System.out.println(message);

				CryptoCompareWebSocketMsgType connectionType = checkConnection(message);
				if (connectionType == null) {
					ws.disconnect();
				} else if (connectionType.getCode() < 400
						|| CryptoCompareWebSocketMsgType.HEARTBEAT.equals(connectionType)) {
					refreshLastActiveTime(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_INACTIVE_JUDGMENT_SECOND);

				} else if (connectionType.getCode() == 500) {
					if (CryptoCompareWebSocketMsgType.FORCE_DISCONNECT.equals(connectionType)) {
						log.error("crypto compare web socket FORCE_DISCONNECT");
						ws.disconnect();
						return;
					} else if (CryptoCompareWebSocketMsgType.RATE_LIMIT_OPENING_SOCKETS_TOO_FAST
							.equals(connectionType)) {
						log.error("crypto compare web socket error: " + connectionType.getName());
						refreshLastActiveTime(CryptoCoinWebSocketConstant.SOCKET_COLDDOWN_SECOND);
						return;
					} else {
						log.error("crypto compare web socket error: " + connectionType.getName());
						refreshLastActiveTime(
								CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_INACTIVE_JUDGMENT_SECOND);
						return;
					}

				} else if (CryptoCompareWebSocketMsgType.TOO_MANY_SOCKETS_MAX_.getCode()
						.equals(connectionType.getCode())) {
					log.error("crypto compare web socket error: " + connectionType.getName());
					refreshLastActiveTime(CryptoCoinWebSocketConstant.SOCKET_COLDDOWN_SECOND);
					return;

				} else {
					log.error("crypto compare web socket error: " + connectionType.getName());
					ws.disconnect();
					return;

				}

				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
//					迁移代码 保存数据
//					croptoCoinPriceCacheDataAckProducer.sendPriceCacheData(dataBO);
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
				redisConnectService
						.deleteValByName(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
			}
		});
		return ws;
	}

	private void refreshLastActiveTime(int seconds) {
		redisConnectService.setValByName(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY,
				localDateTimeHandler.dateToStr(LocalDateTime.now()), seconds, TimeUnit.SECONDS);
	}

	public boolean getSocketLiveFlag() {
		return redisConnectService.hasKey(CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_LAST_ACTIVE_TIME_REDIS_KEY);
	}

	public CommonResult startWebSocket() {
		CryptoCompareSocketConfigBO configBO = getDefaultConfig();
		CommonResult r = new CommonResult();
		if (configBO == null) {
			r.failWithMessage("crypto compare socket load config error");
			return r;
		}

		ws = createWebSocket(configBO);
		if (ws == null) {
			r.failWithMessage("crypto compare socket create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
//			需要重构订阅列表的管理
//			addSubscription(getSubscriptionList());
			r.normalSuccess();
		} catch (WebSocketException e) {
			log.error("crypto compare socket connect error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public void wsDestory() {
		try {
			ws.sendClose();
			ws.disconnect();
		} catch (Exception e) {
			log.error("crypto compare web socket disconnect error: " + e.getLocalizedMessage());
		}
		ws = null;
	}

//	// 需要重构订阅列表的管理
//	public void syncSubscription() {
//		removeAllSubscription();
//		addSubscription(getSubscriptionList());
//	}
//
//	
//	/**
//	 * @param channelStrListchannelStr format 5~CCCAGG~BTC~USD ref:
//	 *                                 https://min-api.cryptocompare.com/documentation/websockets
//	 */
//	public void addSubscription(List<String> channelStrList) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubAdd");
//		JSONArray subs = new JSONArray();
//		String subscription = null;
//		for (int i = 0; i < channelStrList.size(); i++) {
//			subscription = channelStrList.get(i);
//			subscription = "5~CCCAGG~" + subscription.toUpperCase() + "~USDT"; 
//			channelStrList.set(i, subscription);
//			subs.add(subscription);
//		}
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//		
//		addSubscriptionRedisList(channelStrList);
//	}
//
//	public void addSubscription(String channelStr) {
//		JSONObject json = new JSONObject();
//		json.put("action", "SubAdd");
//		JSONArray subs = new JSONArray();
//		channelStr = "5~CCCAGG~" + channelStr.toUpperCase() + "~USDT";
//		subs.add(channelStr);
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//		
//		addSubscriptionRedisList(channelStr);
//	}
//
//	public void removeSubscription(List<String> removeChannelStrList) {
//		if(removeChannelStrList == null || removeChannelStrList.isEmpty()) {
//			return;
//		}
//		
//		for(int i = 0; i < removeChannelStrList.size(); i++) {
//			removeChannelStrList.set(i, "5~CCCAGG~" + removeChannelStrList.get(i).toUpperCase() + "~USDT");
//		}
//		
//		JSONObject json = new JSONObject();
//		json.put("action", "SubRemove");
//		JSONArray subs = new JSONArray();
//
//		
//		for (String subscription : removeChannelStrList) {
//			subs.add(subscription);
//		}
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//		
//		removeSubscriptionRedisList(removeChannelStrList);
//	}
//
//	public void removeAllSubscription() {
//		Long listSize = redisTemplate.opsForList().size(CryptoCoinScheduleClawingConstant.CRYPTO_COMPARE_SUBSCRIPTION_RECORD_REDIS_KEY);
//
//		if (listSize <= 0) {
//			return;
//		}
//		
//		List<String> recordList = new ArrayList<>();
//		String tmpSub = null;
//		for(int i = 0; i < listSize; i++) {
//			tmpSub = String.valueOf(redisTemplate.opsForList().rightPop(CryptoCoinScheduleClawingConstant.CRYPTO_COMPARE_SUBSCRIPTION_RECORD_REDIS_KEY));
//			if(StringUtils.isNotBlank(tmpSub)) {
//				recordList.add(tmpSub);
//			}
//		}
//		
//		if(recordList.isEmpty()) {
//			return;
//		}
//		
//		JSONObject json = new JSONObject();
//		json.put("action", "SubRemove");
//		JSONArray subs = new JSONArray();
//		for (String channelStr : recordList) {
//			subs.add(channelStr);
//		}
//		
//		json.put("subs", subs);
//		ws.sendText(json.toString());
//	}
//
//	public void removeSubscription(String channelStr) {
//		channelStr = channelStr.toUpperCase();
//		channelStr = "5~CCCAGG~" + channelStr.toUpperCase() + "~USDT";
//
//		JSONObject json = new JSONObject();
//		json.put("action", "SubRemove");
//		JSONArray subs = new JSONArray();
//		subs.add(channelStr);
//		json.put("subs", subs);
//
//		ws.sendText(json.toString());
//
//		removeSubscriptionRedisList(channelStr);
//	}
}
