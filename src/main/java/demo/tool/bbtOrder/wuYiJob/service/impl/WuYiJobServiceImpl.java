package demo.tool.bbtOrder.wuYiJob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.bo.WuYiJobClawingBO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.service.TestEventService;
import demo.automationTest.service.impl.AutomationTestCommonService;
import demo.tool.bbtOrder.wuYiJob.service.WuYiJobService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WuYiJobServiceImpl extends AutomationTestCommonService implements WuYiJobService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private TestEventService eventService;
	
	@Override
	public void insertRefreshEvent() {
		String paramSavingPath = getParamFilePath(TestModuleType.SCHEDULE_CLAWING.getModuleName(), ScheduleClawingType.WU_YI_JOB.getFlowName(), WuYiJobClawingBO.class.getSimpleName());
		
		AutomationTestInsertEventDTO insertEventDTO = new AutomationTestInsertEventDTO();
		insertEventDTO.setTestEventId(snowFlake.getNextId());
		insertEventDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		insertEventDTO.setFlowType(ScheduleClawingType.WU_YI_JOB.getId());
		insertEventDTO.setParamStr(ioUtil.getStringFromFile(paramSavingPath));
		
		eventService.insertEvent(insertEventDTO);
		
		testEventInsertAckProducer.send(insertEventDTO);
	}
	
}
