package demo.tool.scheduleClawing.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.automationTest.service.TestEventService;
import demo.common.service.CommonService;

public abstract class ScheduleClawingCommonService extends CommonService {

	@Autowired
	protected ScheduleClawingConstantService constantService;
	@Autowired
	protected TestEventService eventService;
	@Autowired
	protected TestEventInsertAckProducer testEventInsertAckProducer;
	
	protected String getParamFilePath(String flowTypeName, String dtoClassName) {
		String paramSavingPath = constantService.getParamSavingPath();
		return paramSavingPath + File.separator + flowTypeName + File.separator + dtoClassName;
	}
}
