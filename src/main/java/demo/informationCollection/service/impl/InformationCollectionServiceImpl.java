package demo.informationCollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.common.service.CommonService;
import demo.informationCollection.service.InformationCollectionService;

@Service
public class InformationCollectionServiceImpl extends CommonService implements InformationCollectionService {

	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;
	
	@Override
	public void sendEducationInfomationCollectionTask() {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		dto.setFlowType(ScheduleClawingType.EDUCATION_INFO.getId());
		dto.setTestEventId(snowFlake.getNextId());
		testEventInsertAckProducer.send(dto);
	}
	
}
