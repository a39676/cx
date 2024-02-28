package demo.finance.cryptoCoin.data.webSocket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;

import demo.finance.cryptoCoin.data.webSocket.common.CryptoCoinWebSocketCommonClient;
import demo.finance.cryptoCoin.data.webSocket.pojo.bo.BinanceWebScoketConnetionKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinWebSocketConstant;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import net.sf.json.JSONObject;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinanceWSClient2 extends CryptoCoinWebSocketCommonClient {

	private WebSocketStreamClient wsStreamClient = null;
	private Map<BinanceWebScoketConnetionKeyBO, Integer> connectionIdMap = new HashMap<>();
	private String defaultInterval = "1m";

	public void restartWebSocket() {
		wsStreamClient = null;
		createWebSocket();
	}

	public void createWebSocket() {
		createWebSorkcetIfNecessary();
		List<String> symbolList = new ArrayList<>(getSubscriptionList());
		for (String symbol : symbolList) {
			if (StringUtils.isNotBlank(symbol)) {
				addNewSubcript(symbol + CurrencyTypeForCryptoCoin.USDT.getName(), defaultInterval);
			}
		}
	}

	private WebSocketStreamClient createWebSorkcetIfNecessary() {
		if (wsStreamClient == null) {
			wsStreamClient = new WebSocketStreamClientImpl();
		}

		return wsStreamClient;
	}

	public void addNewSubcript(String symbol, String interval) {
		createWebSorkcetIfNecessary();
		BinanceWebScoketConnetionKeyBO keyBO = new BinanceWebScoketConnetionKeyBO();
		keyBO.setInterval(interval);
		keyBO.setSymbol(symbol);
		Integer connectionId = connectionIdMap.get(keyBO);
		if (connectionId != null) {
			wsStreamClient.closeConnection(connectionId);
			connectionIdMap.remove(keyBO);
			log.error("Binance web socket remove kline connection, symbol: " + symbol + ", interval: " + interval
					+ ", ID: " + connectionId);
		}

		connectionId = wsStreamClient.klineStream(symbol, interval, buildKLineDataCallback());
		log.error("Binance web socket add kline connection, symbol: " + symbol + ", interval: " + interval + ", ID: "
				+ connectionId);
		connectionIdMap.put(keyBO, connectionId);
	}

	private WebSocketMessageCallback buildKLineDataCallback() {
		WebSocketMessageCallback kLineDataCallback = new WebSocketMessageCallback() {

			@Override
			public void onMessage(String message) {
				refreshLastActiveTime();

				CryptoCoinPriceCommonDataBO dataBO = buildCommonDataFromMsg(message);
				if (dataBO != null) {
					cacheService.reciveData(dataBO);
				}
			}
		};
		return kLineDataCallback;
	}

	public void killStream() {
		createWebSorkcetIfNecessary();
		wsStreamClient.closeAllConnections();
	}

	private void refreshLastActiveTime() {
		constantService.setBinanceWebSocketLastActiveTime(LocalDateTime.now());
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
				bo.setCurrencyType(CurrencyTypeForCryptoCoin.USD.getCode());
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

	public boolean getSocketLiveFlag() {
		LocalDateTime lastActiveTime = constantService.getBinanceWebSocketLastActiveTime();
		if (lastActiveTime == null) {
			return false;
		}
		long seconds = ChronoUnit.SECONDS.between(lastActiveTime, LocalDateTime.now());

		return CryptoCoinWebSocketConstant.BINANCE_SOCKET_INACTIVE_JUDGMENT_SECOND > seconds;
	}
}
