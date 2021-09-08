package demo.automationTest.service;

import at.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface AutomationTestReportService {

	boolean checkReportSavingFloder(String path);

	CommonResult saveReport(AutomationTestResultDTO dto);
	
	JsonReportOfFlowDTO buildReportFromMQ(JSONObject json);

	JsonReportOfFlowDTO buildReportFromDatabase(JSONObject json);

}
