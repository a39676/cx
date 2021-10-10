package demo.automationTest.service;

import java.time.LocalDateTime;
import java.util.List;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import net.sf.json.JSONObject;

public interface AutomationTestReportService {

	boolean checkReportSavingFloder(String path);

	CommonResult saveReport(AutomationTestResultDTO dto);
	
	JsonReportOfFlowDTO buildReportFromMQ(JSONObject json);

	JsonReportOfFlowDTO buildReportFromDatabase(JSONObject json);

	List<TestEvent> findReportPage(FindTestEventPageByConditionDTO dto);

	void deleteOldData(LocalDateTime limitDateTime);

}
