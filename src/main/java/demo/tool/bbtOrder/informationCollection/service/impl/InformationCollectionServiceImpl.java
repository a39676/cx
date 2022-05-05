package demo.tool.bbtOrder.informationCollection.service.impl;

import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.tool.bbtOrder.informationCollection.service.InformationCollectionService;
import demo.tool.bbtOrder.service.BbtOrderCommonService;

@Service
public class InformationCollectionServiceImpl extends BbtOrderCommonService implements InformationCollectionService {

	@Override
	public void sendEducationInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.EDUCATION_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
	
}
