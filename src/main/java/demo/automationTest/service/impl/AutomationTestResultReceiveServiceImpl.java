package demo.automationTest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.report.service.ATJsonReportService;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.AutomationTestResultReceiveService;

@Service
public class AutomationTestResultReceiveServiceImpl extends AutomationTestCommonService
		implements AutomationTestResultReceiveService {

	@Autowired
	private AutomationTestReportService reportService;
	@Autowired
	private ATJsonReportService jsonReportService;

	@Override
	public void handleAutomationTestResult(String messageStr) {
		AutomationTestResultDTO dto;
		try {
			dto = buildObjFromJsonCustomization(messageStr, AutomationTestResultDTO.class);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("receive error automation test result: " + messageStr);
			return;
		}

		TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
		if (po == null) {
			return;
		}

		reportPrefixHandle(dto);
		CommonResult saveReportResult = reportService.saveReport(dto);
		if (saveReportResult.isSuccess()) {
			po.setReportPath(saveReportResult.getMessage());
		} else {
			log.error("automation test report saving error: " + dto.getTestEventId());
		}

		updateCaseCounting(po, dto);

		po.setIsPass(true);
		for (AutomationTestCaseResult subResult : dto.getCaseResultList()) {
			if (!AutomationTestFlowResultType.PASS.equals(subResult.getResultType())) {
				po.setIsPass(false);
				break;
			}
		}

		po.setRemark(dto.getRemark());
		po.setStartTime(dto.getStartTime());
		po.setEndTime(dto.getEndTime());
		eventMapper.updateByPrimaryKeySelective(po);
	}

	private void reportPrefixHandle(AutomationTestResultDTO dto) {
		JsonReportOfFlowDTO eventReport = dto.getReport();
		List<AutomationTestCaseResult> caseResultList = dto.getCaseResultList();

		JsonReportOfCaseDTO tmpCaseReport = null;
		AutomationTestCaseResult tmpCaseResult = null;
		for (int i = 0; i < caseResultList.size() && i < eventReport.getCaseReportList().size(); i++) {
			tmpCaseReport = eventReport.getCaseReportList().get(i);
			tmpCaseResult = caseResultList.get(i);
			jsonReportService.caseReportAppendContent(tmpCaseReport, tmpCaseResult.getResultType().getName(), 0);
		}

	}

	private void updateCaseCounting(TestEvent po, AutomationTestResultDTO dto) {
		List<AutomationTestCaseResult> resultList = dto.getCaseResultList();
		int passCaseCount = 0;
		int failCaseCount = 0;
		int blockCaseCount = 0;
		for (AutomationTestCaseResult caseResult : resultList) {
			if (AutomationTestFlowResultType.PASS.equals(caseResult.getResultType())) {
				passCaseCount++;
			} else if (AutomationTestFlowResultType.FAILED.equals(caseResult.getResultType())) {
				failCaseCount++;
			} else if (AutomationTestFlowResultType.BLOCKED.equals(caseResult.getResultType())) {
				blockCaseCount++;
			}
		}
		po.setPassCount(passCaseCount);
		po.setFailCount(failCaseCount);
		po.setBlockCount(blockCaseCount);
	}

}
