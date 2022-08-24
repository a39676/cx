package demo.tool.bbtOrder.informationCollection.service.impl;

import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.service.impl.AutomationTestCommonService;
import demo.tool.bbtOrder.informationCollection.service.InformationCollectionService;

@Service
public class InformationCollectionServiceImpl extends AutomationTestCommonService implements InformationCollectionService {

	@Override
	public void sendEducationInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.EDUCATION_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
	
	@Override
	public void sendV2exJobInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.V2EX_JOB_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
	
}
