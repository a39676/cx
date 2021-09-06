package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.data.webSocket.BinanceWSClient;
import demo.finance.cryptoCoin.data.webSocket.CryptoCompareWSClient;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;

@Component
public class CryptoCoinTaskService {

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private BinanceWSClient binanceWSClient;

	@Autowired
	private CryptoCompareWSClient cryptoCompareWSClient;

	@Scheduled(cron = "0/10 * * ? * * ")
	public void checkWebSocketStatus() {
		if (!binanceWSClient.getSocketLiveFlag()) {
			telegramService.sendMessage(TelegramBotType.BOT_2, "binance socket down", TelegramStaticChatID.MY_ID);
			binanceWSClient.startWebSocket();
		}
		
		if (!cryptoCompareWSClient.getSocketLiveFlag()) {
			telegramService.sendMessage(TelegramBotType.BOT_2, "crypto comapre socket down",
					TelegramStaticChatID.MY_ID);
			cryptoCompareWSClient.startWebSocket();
		}

	}
}
