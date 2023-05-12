//package demo.finance.cryptoCoin.data.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import demo.base.task.pojo.dto.SendTaskDTO;
//import demo.base.task.pojo.type.TaskType;
//import demo.base.task.service.CommonTaskService;
//import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinDataTaskType;
//import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
//import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
//import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
//import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
//import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
//
//@Component
//public class CryptoCoinDataTaskService extends CommonTaskService {
//
//	@Autowired
//	private CryptoCoin1MinuteDataSummaryService cryptoCoin1MinuteDataSummaryService;
//	@Autowired
//	private CryptoCoin5MinuteDataSummaryService cryptoCoin5MinuteDataSummaryService;
//	@Autowired
//	private CryptoCoin60MinuteDataSummaryService cryptoCoin60MinuteDataSummaryService;
//	@Autowired
//	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
//	@Autowired
//	private CryptoCoin1MonthDataSummaryService cryptoCoin1MonthDataSummaryService;
//	
//
//	@Scheduled(cron = "0 */2 * * * ?")
//	public void summaryMinuteDataTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_DATA);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinDataTaskType subTaskType = CryptoCoinDataTaskType.SUMMARY_MINUTE_DATA;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void summaryMinuteData() {
//		cryptoCoin1MinuteDataSummaryService.summaryLowPriceRedisData();
//	}
//	
//	@Scheduled(cron="20 19 03 * * *")
//	public void summaryHistoryDataTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_DATA);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinDataTaskType subTaskType = CryptoCoinDataTaskType.SUMMARY_HISTORY_DATA;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void summaryHistoryData() {
//		cryptoCoin5MinuteDataSummaryService.summaryHistoryData();
//		cryptoCoin60MinuteDataSummaryService.summaryHistoryData();
//		cryptoCoin1MonthDataSummaryService.summaryHistoryData();
//	}
//
//	@Scheduled(fixedDelay = 1000L * 60 * 10)
//	public void deleteExpiredCacheDataTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_DATA);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinDataTaskType subTaskType = CryptoCoinDataTaskType.DELETE_EXPIRED_CACHE_DATA;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void deleteExpiredCacheData() {
//		cryptoCoin1MinuteDataSummaryService.deleteExpiredCacheData();
//		cryptoCoin5MinuteDataSummaryService.deleteExpiredCacheData();
//		cryptoCoin60MinuteDataSummaryService.deleteExpiredCacheData();
//	}
//
//	@Scheduled(cron="20 19 01 * * *")
//	@Scheduled(cron="20 19 03 * * *")
//	@Scheduled(cron="20 19 05 * * *")
//	public void sendCryptoCoinDailyDataQueryMsgTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_DATA);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinDataTaskType subTaskType = CryptoCoinDataTaskType.SEND_CRYPTO_COIN_DAILY_DATA_QUERY_MSG;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void sendCryptoCoinDailyDataQueryMsg() {
//		cryptoCoin1DayDataSummaryService.sendAllCryptoCoinDailyDataQueryMsg();
//	}
//	
//	@Scheduled(cron="00 00 00 * * *")
//	public void resetDailyDataWaitingQuerySetTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_DATA);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinDataTaskType subTaskType = CryptoCoinDataTaskType.RESET_DAILY_DATA_WAITING_QUERY_SET;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void resetDailyDataWaitingQuerySet() {
//		cryptoCoin1DayDataSummaryService.resetDailyDataWaitingQuerySet();
//	}
//	
////	@Autowired
////	private CryptoCoinLowPriceNoticeService cryptoCoinLowPriceNoticeService;
////	@Scheduled(cron="56 45 7 * * *")
////	@Scheduled(cron="56 45 15 * * *")
////	@Scheduled(cron="56 45 23 * * *")
////	public void setNewLowPriceSubscription() {
////		cryptoCoinLowPriceNoticeService.setNewLowPriceSubscription();
////	}
////	
////	@Scheduled(cron="56 05 8 * * *")
////	@Scheduled(cron="56 05 16 * * *")
////	@Scheduled(cron="56 05 0 * * *")
////	public void setLowPriceCoinWatching() {
////		cryptoCoinLowPriceNoticeService.setLowPriceCoinWatching();
////	}
//}
