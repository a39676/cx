package demo.finance.cryptoCoin.data.webSocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import demo.finance.cryptoCoin.data.webSocket.common.CryptoCoinWebSocketCommonClient;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import net.sf.json.JSONObject;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinanceWSClient extends CryptoCoinWebSocketCommonClient {

	private WebSocket ws = null;

	public WebSocket getWs() {
		if (ws == null) {
			createWebSocket();
		}
		return ws;
	}

//	   binance web scoket kline respon exaple 
//	   https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md
//	    {
//		  "e": "kline", // Event type
//		  "E": 1616819474380, // Event time
//		  "s": "FILUSDT",  // Symbol
//		  "k": {
//		    "t": 1616819460000,  // Kline start time
//		    "T": 1616819519999,  // Kline close time
//		    "s": "FILUSDT",  // Symbol
//		    "i": "1m",  // Interval
//		    "f": 20019412,  // First trade ID
//		    "L": 20019875,  // Last trade ID
//		    "o": "128.13010000",  // Open price
//		    "c": "128.37060000",  // Close price
//		    "h": "128.47660000",  // High price
//		    "l": "128.02660000",  // Low price
//		    "v": "1444.36000000",  // Base asset volume 交易币数
//		    "n": 464,  // Number of trades
//		    "x": false,  // Is this kline closed?
//		    "q": "185297.51924800",  // Quote asset volume 交易金额
//		    "V": "989.65000000",  // Taker buy base asset volume
//		    "Q": "126963.71005400",  // Taker buy quote asset volume
//		    "B": "0"  // Ignore
//		  }
//		}

	private CryptoCoinPriceCommonDataBO buildCommonDataFromMsg(String sourceMsg) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject sourceMsgJson = JSONObject.fromObject(sourceMsg);
			if (!sourceMsgJson.containsKey("k")) {
				log.error("binnace recive unknow: " + sourceMsg);
				return null;
			}
			bo = new CryptoCoinPriceCommonDataBO();

			JSONObject kDataJson = sourceMsgJson.getJSONObject("k");
			String symbol = sourceMsgJson.getString("s").toLowerCase();

			if (symbol.contains("usdt")) {
				bo.setCurrencyType(CurrencyType.USD.getCode());
				bo.setCoinType(symbol.replaceAll("usdt", ""));
			} else {
				return null;
			}

			bo.setStartPrice(new BigDecimal(kDataJson.getDouble("o")));
			bo.setEndPrice(new BigDecimal(kDataJson.getDouble("c")));
			bo.setHighPrice(new BigDecimal(kDataJson.getDouble("h")));
			bo.setLowPrice(new BigDecimal(kDataJson.getDouble("l")));
			bo.setVolume(new BigDecimal(kDataJson.getDouble("v")));

			try {
				Date tradDate = new Date(sourceMsgJson.getLong("E"));
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
			return null;
		}
		return bo;
	}

	private WebSocket createWebSocket() {
		StringBuffer uriBuilder = new StringBuffer(optionService.getBinanceUri());
		uriBuilder.append("/ws");
		List<String> symbolList = new ArrayList<>(getSubscriptionList());
		log.error("binance socket get symbolList: " + symbolList);
		for (String symbol : symbolList) {
			if (StringUtils.isNotBlank(symbol)) {
				uriBuilder.append("/" + symbol.toLowerCase() + "usdt" + "@kline_1m");
			}
		}
		log.error("binance url: " + uriBuilder.toString());
		try {
			ws = new WebSocketFactory().setVerifyHostname(false).createSocket(uriBuilder.toString());
			refreshLastActiveTime();
			return ws;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("binance create socket error: " + e);
			return null;
		}
	}

	private WebSocket setListener(WebSocket ws) {
		ws.addListener(new WebSocketAdapter() {
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws Exception {
//				System.out.println(message);
//				log.error("binan message: " + message);
				refreshLastActiveTime();

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
		constantService.setBinanceWebSocketLastActiveTime(LocalDateTime.now());
	}

	public boolean getSocketLiveFlag() {
		LocalDateTime lastActiveTime = constantService.getBinanceWebSocketLastActiveTime();
		if (lastActiveTime == null) {
			return false;
		}
		long seconds = ChronoUnit.SECONDS.between(lastActiveTime, LocalDateTime.now());

		return CryptoCoinWebSocketConstant.BINANCE_SOCKET_INACTIVE_JUDGMENT_SECOND > seconds;
	}

	public CommonResult restartWebSocket() {
		CommonResult r = new CommonResult();

		wsDestory();
		
		ws = createWebSocket();
		if (ws == null) {
			r.failWithMessage("binance socket create scoket error");
			return r;
		}

		ws = setListener(ws);
		try {
			ws.connect();
			r.normalSuccess();
		} catch (WebSocketException e) {
			log.error("binance socket connect error: " + e.getLocalizedMessage());
		}

		return r;
	}

	public void wsDestory() {
		try {
			ws.sendClose();
			ws.disconnect();
		} catch (Exception e) {
			log.error("binance web socket disconnect error: " + e.getLocalizedMessage());
		}
		ws = null;
	}

}
