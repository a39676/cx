package demo.finance.metal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.metal.service.PreciousMetalService;

@Component
public class FinanceTaskToolServiceImpl {
	
	@Autowired
	private PreciousMetalService preciousMetalService;

	@Scheduled(cron="0 */1 * * * ?")
	public void preciousMetalPriceNoticeHandler() {
		preciousMetalService.noticeHandler();
	}
}
