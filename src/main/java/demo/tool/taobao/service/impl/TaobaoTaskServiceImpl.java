package demo.tool.taobao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.tool.taobao.service.TaobaoUpstreamSupplierService;

@Component
public class TaobaoTaskServiceImpl extends CommonTaskService {

	@Autowired
	private TaobaoUpstreamSupplierService upstreamSupplierService;

	@Scheduled(cron = "23 57 19 * * *")
	public void supplierHeatReduct() {
		upstreamSupplierService.supplierHeatReduct();
	}
}
