package demo.tool.bbtOrder.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.automationTest.service.TestEventService;
import demo.common.service.CommonService;

public abstract class BbtOrderCommonService extends CommonService {

	@Autowired
	protected BbtOrderOptionService optionService;
	@Autowired
	protected TestEventService eventService;
	@Autowired
	protected TestEventInsertAckProducer testEventInsertAckProducer;
	
	protected String getParamFilePath(String flowTypeName, String dtoClassName) {
		String paramSavingPath = optionService.getParamSavingPath();
		return paramSavingPath + File.separator + flowTypeName + File.separator + dtoClassName;
	}
}
