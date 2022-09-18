package demo.automationTest.service;

import java.time.LocalDateTime;
import java.util.List;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.pojo.vo.TestReportSummaryVO;

public interface AutomationTestReportService {

	boolean checkReportSavingFloder(String path);

	CommonResult saveReport(AutomationTestResultDTO dto);
	
	List<TestEvent> findReportPage(FindTestEventPageByConditionDTO dto);

	void deleteOldData(LocalDateTime limitDateTime);

	TestReportSummaryVO buildReportSummaryVO(TestEvent po);

}
