//package demo.finance.cryptoCoin.notice.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import demo.base.task.pojo.dto.SendTaskDTO;
//import demo.base.task.pojo.type.TaskType;
//import demo.base.task.service.CommonTaskService;
//import demo.finance.cryptoCoin.notice.pojo.type.CryptoCoinNoticeTaskType;
//import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
//
//@Component
//public class CryptoCoinNoticeTaskService extends CommonTaskService {
//
//	@Autowired
//	private CryptoCoinCommonNoticeService noticeService;
//
//	@Scheduled(fixedDelay = 1000L * 30)
//	public void cryptoCoinPriceNoticeHandlerTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_NOTICE);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinNoticeTaskType subTaskType = CryptoCoinNoticeTaskType.CRYPTO_COIN_PRICE_NOTICE_HANDLER;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void cryptoCoinPriceNoticeHandler() {
//		noticeService.noticeHandler();
//	}
//
//	@Scheduled(cron = "40 51 22 * * *")
//	public void deleteOldNoticeTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_NOTICE);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinNoticeTaskType subTaskType = CryptoCoinNoticeTaskType.DELETE_OLD_NOTICE;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void deleteOldNotice() {
//		noticeService.deleteOldNotice();
//	}
//
//}
