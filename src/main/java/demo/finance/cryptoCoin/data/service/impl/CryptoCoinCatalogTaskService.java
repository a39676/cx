//package demo.finance.cryptoCoin.data.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import demo.base.task.pojo.dto.SendTaskDTO;
//import demo.base.task.pojo.type.TaskType;
//import demo.base.task.service.CommonTaskService;
//import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
//import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinCatalogTaskType;
//import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
//
//@Component
//public class CryptoCoinCatalogTaskService extends CommonTaskService {
//
//	@Autowired
//	private CryptoCoinCatalogService catalogService;
//	@Autowired
//	private CryptoCoinOptionService optionService;
//
//	@Scheduled(fixedDelay = 1000L * 60 * 5)
//	public void addSubscriptionCatalogTask() {
//		SendTaskDTO dto = new SendTaskDTO();
//		dto.setFirstTask(TaskType.CRYPTO_COIN_CATALOG);
//		dto.setTaskId(snowFlake.getNextId());
//		
//		CryptoCoinCatalogTaskType subTaskType = CryptoCoinCatalogTaskType.ADD_SUBSCRIPTION_CATALOG;
//		dto.setTaskSecondCode(subTaskType.getCode());
//		dto.setTaskSecondName(subTaskType.getName());
//		
//		taskInsertAckProducer.send(dto);
//	}
//	
//	public void addSubscriptionCatalog() {
//		catalogService.addSubscriptionCatalog(optionService.getDefaultCoinCatalog());
//	}
//	
//}
