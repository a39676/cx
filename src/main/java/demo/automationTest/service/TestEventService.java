package demo.automationTest.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;

public interface TestEventService {

	void insertEvent(AutomationTestInsertEventDTO dto);

	void sendTestEventToRun();

	void handleLongWaitingEvent();

}
