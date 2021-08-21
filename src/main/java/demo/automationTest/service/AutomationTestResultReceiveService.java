package demo.automationTest.service;

import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;

public interface AutomationTestResultReceiveService {

	void savingReport(AutomationTestResultDTO dto);

}
