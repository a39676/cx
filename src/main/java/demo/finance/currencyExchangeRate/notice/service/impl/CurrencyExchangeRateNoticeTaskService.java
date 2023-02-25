package demo.finance.currencyExchangeRate.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.finance.currencyExchangeRate.notice.pojo.type.CurrencyExchangeRateNoticeTaskType;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Component
public class CurrencyExchangeRateNoticeTaskService extends CommonTaskService {

	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;

	@Scheduled(cron = "0 28 3 * * ?")
	public void deleteOldNoticeTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CURRENCY_EXCHANGE_RATE_NOTICE);
		dto.setTaskId(snowFlake.getNextId());

		CurrencyExchangeRateNoticeTaskType subTaskType = CurrencyExchangeRateNoticeTaskType.DELETE_OLD_NOTICE;
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());

		taskInsertAckProducer.send(dto);
	}

	public void deleteOldNotice() {
		currencyExchangeRateNoticeService.deleteOldNotice();
	}

}
