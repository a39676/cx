package demo.finance.cryptoCoin.data.webSocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCompareWebSocketMsgType;
import demo.finance.cryptoCoin.data.webSocket.common.CryptoCoinWebSocketCommonClient;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CryptoCompareWSClient extends CryptoCoinWebSocketCommonClient {

	private WebSocket ws = null;

	public WebSocket getWs() {
		if (ws == null) {
			createWebSocket();
		}
		return ws;
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

	private WebSocket createWebSocket() {
		String uriStr = constantService.getCryptoCompareUri() + "?api_key=" + constantService.getCryptoCompareApiKey();
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
					refreshLastActiveTime();

				} else if (connectionType.getCode() == 500) {
					if (CryptoCompareWebSocketMsgType.FORCE_DISCONNECT.equals(connectionType)) {
						log.error("crypto compare web socket FORCE_DISCONNECT");
						ws.disconnect();
						return;
					} else if (CryptoCompareWebSocketMsgType.RATE_LIMIT_OPENING_SOCKETS_TOO_FAST
							.equals(connectionType)) {
						log.error("crypto compare web socket error: " + connectionType.getName());
						refreshLastActiveTime();
						return;
					} else {
						log.error("crypto compare web socket error: " + connectionType.getName());
						refreshLastActiveTime();
						return;
					}

				} else if (CryptoCompareWebSocketMsgType.TOO_MANY_SOCKETS_MAX_.getCode()
						.equals(connectionType.getCode())) {
					log.error("crypto compare web socket error: " + connectionType.getName());
					refreshLastActiveTime();
					return;

				} else {
					log.error("crypto compare web socket error: " + connectionType.getName());
					ws.disconnect();
					return;

				}

				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
					cacheService.reciveData(dataBO);
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
					WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
			}
		});
		return ws;
	}

	private void refreshLastActiveTime() {
		constantService.setCryptoCompareWebSocketLastActiveTime(LocalDateTime.now());
	}

	public boolean getSocketLiveFlag() {
		LocalDateTime lastActiveTime = constantService.getBinanceWebSocketLastActiveTime();
		if (lastActiveTime == null) {
			return false;
		}
		long seconds = ChronoUnit.SECONDS.between(lastActiveTime, LocalDateTime.now());

		return CryptoCoinWebSocketConstant.CRYPTO_COMPARE_SOCKET_INACTIVE_JUDGMENT_SECOND > seconds;
	}

	public CommonResult startWebSocket() {
		CommonResult r = new CommonResult();

		ws = createWebSocket();
		if (ws == null) {
			r.failWithMessage("crypto compare socket create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
			addSubscription(new ArrayList<String>(getSubscriptionList()));
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

	public void restart() {
		wsDestory();
		startWebSocket();
	}

	/**
	 * @param channelStrListchannelStr format 5~CCCAGG~BTC~USD ref:
	 *                                 https://min-api.cryptocompare.com/documentation/websockets
	 */
	public void addSubscription(List<String> channelStrList) {
		JSONObject json = new JSONObject();
		json.put("action", "SubAdd");
		JSONArray subs = new JSONArray();
		for (String subscription : channelStrList) {
			subscription = subscription.toUpperCase();
			subscription = "5~CCCAGG~" + subscription.toUpperCase() + "~USDT";
			subs.add(subscription);
		}
		json.put("subs", subs);

		ws.sendText(json.toString());

		addMainSubscription(channelStrList);
	}

	public void addSubscription(String channelStr) {
		JSONObject json = new JSONObject();
		json.put("action", "SubAdd");
		JSONArray subs = new JSONArray();
		channelStr = channelStr.toUpperCase();
		channelStr = "5~CCCAGG~" + channelStr.toUpperCase() + "~USDT";
		subs.add(channelStr);
		json.put("subs", subs);

		ws.sendText(json.toString());

		addMainSubscription(channelStr);
	}

	public void removeSubscription(List<String> removeChannelStrList) {
		if (removeChannelStrList == null || removeChannelStrList.isEmpty()) {
			return;
		}

		List<String> resultList = new ArrayList<>();
		String channel = null;

		for (int i = 0; i < removeChannelStrList.size(); i++) {
			channel = channel.toUpperCase();
			resultList.set(i, "5~CCCAGG~" + removeChannelStrList.get(i).toUpperCase() + "~USDT");
		}

		JSONObject json = new JSONObject();
		json.put("action", "SubRemove");
		JSONArray subs = new JSONArray();

		for (String subscription : resultList) {
			subs.add(subscription);
		}
		json.put("subs", subs);

		ws.sendText(json.toString());

		removeMainSubscription(removeChannelStrList);
	}

	public void removeAllSubscription() {
		if (constantService.getSubscriptionSet() == null || constantService.getSubscriptionSet().isEmpty()) {
			return;
		}

		List<String> tmpList = new ArrayList<>();
		tmpList.addAll(constantService.getSubscriptionSet());
		String channel = null;

		for (int i = 0; i < tmpList.size(); i++) {
			channel = channel.toUpperCase();
			tmpList.add("5~CCCAGG~" + tmpList.get(i).toUpperCase() + "~USDT");
		}

		JSONObject json = new JSONObject();
		json.put("action", "SubRemove");
		JSONArray subs = new JSONArray();
		for (String channelStr : tmpList) {
			subs.add(channelStr);
		}

		json.put("subs", subs);
		ws.sendText(json.toString());

		removeAllMainSubscription();
	}

	public void removeSubscription(String channelStr) {
		channelStr = channelStr.toUpperCase();
		channelStr = "5~CCCAGG~" + channelStr.toUpperCase() + "~USDT";

		JSONObject json = new JSONObject();
		json.put("action", "SubRemove");
		JSONArray subs = new JSONArray();
		subs.add(channelStr);
		json.put("subs", subs);

		ws.sendText(json.toString());

		removeMainSubscription(channelStr);
	}
}
