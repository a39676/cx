package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.data.webSocket.BinanceWSClient;
import demo.finance.cryptoCoin.data.webSocket.CryptoCompareWSClient;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;

@Component
public class CryptoCoinTaskService extends CommonService {

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
	
	

	@Scheduled(cron = "* */11 * * * ?")
	public void checkWebSocketStatus() {
		if(!systemConstantService.isDev()) {
			try {
				if (!binanceWSClient.getSocketLiveFlag()) {
					telegramService.sendMessage(TelegramBotType.BOT_2, "binance socket down", TelegramStaticChatID.MY_ID);
					binanceWSClient.startWebSocket();
				}
			} catch (Exception e) {
				log.error("create Binance web socket error: " + e.getLocalizedMessage());
				log.error(e.getMessage());
				log.error(e.toString());
			}
			
			try {
				if (!cryptoCompareWSClient.getSocketLiveFlag()) {
					telegramService.sendMessage(TelegramBotType.BOT_2, "crypto comapre socket down",
							TelegramStaticChatID.MY_ID);
					cryptoCompareWSClient.startWebSocket();
				}
			} catch (Exception e) {
			}
		}
	}
	
	@Scheduled(fixedRate = 60000)
	public void cleanOldHistoryData() {
		cacheService.cleanOldHistoryData();
	}
	
}
