package demo.automationTest.service;

import at.report.pojo.dto.JsonReportOfEventDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface AutomationTestReportService {

	JsonReportOfEventDTO buildReportFromJson(JSONObject json);

	boolean checkReportSavingFloder(String path);

	CommonResult saveReport(AutomationTestResultDTO dto);

}
