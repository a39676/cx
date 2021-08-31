package demo.automationTest.service;

public interface AutomationTestResultReceiveService {

	/**
	 * input MQ message string
	 * @param messageStr
	 */
	void handleAutomationTestResult(String messageStr);

}
