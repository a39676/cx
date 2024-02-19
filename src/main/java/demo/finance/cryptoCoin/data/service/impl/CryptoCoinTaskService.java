package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.SystemOptionService;
import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.data.webSocket.BinanceWSClient;
import demo.finance.cryptoCoin.data.webSocket.CryptoCompareWSClient;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
public class CryptoCoinTaskService extends CommonTaskService {

	@Autowired
	private SystemOptionService systemConstantService;
	@Autowired
	private TelegramService telegramService;
	@Autowired
	private BinanceWSClient binanceWSClient;
	@Autowired
	private CryptoCompareWSClient cryptoCompareWSClient;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	@Autowired
	protected CryptoCoinOptionService optionService;
	
	

	@Scheduled(fixedDelay = 1000L * 60 * 10)
	public void checkWebSocketStatus() {
		if(!systemConstantService.isDev()) {
			try {
				if (optionService.getBinanceWebSocketTurnOn() && !binanceWSClient.getSocketLiveFlag()) {
					telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, "binance socket down", TelegramStaticChatID.MY_ID);
					binanceWSClient.restartWebSocket();
				}
			} catch (Exception e) {
				log.error("create Binance web socket error: " + e.getLocalizedMessage());
				log.error(e.getMessage());
				log.error(e.toString());
			}
			
			try {
				if (optionService.getCryptoCompareWebSocketTurnOn() && !cryptoCompareWSClient.getSocketLiveFlag()) {
					telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, "crypto comapre socket down",
							TelegramStaticChatID.MY_ID);
					cryptoCompareWSClient.restart();
				}
			} catch (Exception e) {
			}
		}
	}
	
	@Scheduled(fixedDelay = 60000)
	public void cleanOldHistoryData() {
		cacheService.cleanOldHistoryData();
	}
	
}
