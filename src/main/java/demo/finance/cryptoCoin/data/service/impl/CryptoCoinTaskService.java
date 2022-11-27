package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.SystemOptionService;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinTaskType;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.data.webSocket.BinanceWSClient;
import demo.finance.cryptoCoin.data.webSocket.CryptoCompareWSClient;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

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
	
	

	@Scheduled(cron = "* */11 * * * ?")
	public void checkWebSocketStatus() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CRYPTO_COIN);
		dto.setTaskId(snowFlake.getNextId());
		
		CryptoCoinTaskType articleTaskType = CryptoCoinTaskType.CHECK_WEB_SOCKET_STATUS;
		dto.setTaskSecondCode(articleTaskType.getCode());
		dto.setTaskSecondName(articleTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}

	public void checkWebSocketStatusTask() {
		if(!systemConstantService.isDev()) {
			try {
				if (!binanceWSClient.getSocketLiveFlag()) {
					telegramService.sendMessageByChatRecordId(TelegramBotType.BOT_2, "binance socket down", TelegramStaticChatID.MY_ID);
					binanceWSClient.restartWebSocket();
				}
			} catch (Exception e) {
				log.error("create Binance web socket error: " + e.getLocalizedMessage());
				log.error(e.getMessage());
				log.error(e.toString());
			}
			
			try {
				if (!cryptoCompareWSClient.getSocketLiveFlag()) {
					telegramService.sendMessageByChatRecordId(TelegramBotType.BOT_2, "crypto comapre socket down",
							TelegramStaticChatID.MY_ID);
					cryptoCompareWSClient.restart();
				}
			} catch (Exception e) {
			}
		}
	}
	
	@Scheduled(fixedRate = 60000)
	public void cleanOldHistoryData() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CRYPTO_COIN);
		dto.setTaskId(snowFlake.getNextId());
		
		CryptoCoinTaskType articleTaskType = CryptoCoinTaskType.CLEAN_OLD_HISTORY_DATA;
		dto.setTaskSecondCode(articleTaskType.getCode());
		dto.setTaskSecondName(articleTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}

	public void cleanOldHistoryDataTask() {
		cacheService.cleanOldHistoryData();
	}
	
}
