package demo.tool.bbtOrder.wuYiJob.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.bo.WuYiJobClawingBO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.tool.bbtOrder.service.BbtOrderCommonService;
import demo.tool.bbtOrder.wuYiJob.service.WuYiJobService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WuYiJobServiceImpl extends BbtOrderCommonService implements WuYiJobService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public void insertRefreshEvent() {
		String paramSavingPath = getParamFilePath(ScheduleClawingType.WU_YI_JOB.getFlowName(), WuYiJobClawingBO.class.getSimpleName());
		
		AutomationTestInsertEventDTO insertEventDTO = new AutomationTestInsertEventDTO();
		insertEventDTO.setTestEventId(snowFlake.getNextId());
		insertEventDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		insertEventDTO.setFlowType(ScheduleClawingType.WU_YI_JOB.getId());
		insertEventDTO.setParamStr(ioUtil.getStringFromFile(paramSavingPath));
		
		eventService.insertEvent(insertEventDTO);
		
		testEventInsertAckProducer.send(insertEventDTO);
	}
	
}
