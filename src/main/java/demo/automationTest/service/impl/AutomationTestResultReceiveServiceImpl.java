package demo.automationTest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportOfCaseDTO;
import at.report.pojo.dto.JsonReportOfEventDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.AutomationTestResultReceiveService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AutomationTestResultReceiveServiceImpl extends AutomationTestCommonService
		implements AutomationTestResultReceiveService {

	@Autowired
	private AutomationTestReportService reportService;

	@Override
	public void handleAutomationTestResult(String messageStr) {
		AutomationTestResultDTO dto;
		try {
			dto = messageStrToAutomationTestResultDTO(messageStr);
		} catch (Exception e1) {
			log.error("receive error automation test result: " + messageStr);
			return;
		}

		TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
		if (po == null) {
			return;
		}
		
		reportPrefixHandle(dto);
		CommonResult saveReportResult = reportService.saveReport(dto);
		if(saveReportResult.isSuccess()) {
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

	private AutomationTestResultDTO messageStrToAutomationTestResultDTO(String messageStr) throws Exception {
		AutomationTestResultDTO dto = new AutomationTestResultDTO();

		JSONObject json = null;
		try {
			json = JSONObject.fromObject(messageStr);
		} catch (Exception e) {
			throw new Exception();
		}

		dto.setTestEventId(json.getLong("testEventId"));
		dto.setEndTime(localDateTimeHandler.localDateTimeJsonStrToLocalDateTime(json.getString("endTime")));
		dto.setStartTime(localDateTimeHandler.localDateTimeJsonStrToLocalDateTime(json.getString("startTime")));
		dto.setCaseResultList(buildCaseResultList(json));
		dto.setReport(reportService.buildReportFromJson(json));
		dto.setRemark(json.getString("remark"));

		return dto;
	}

	private List<AutomationTestCaseResult> buildCaseResultList(JSONObject json) {
		List<AutomationTestCaseResult> caseResultList = new ArrayList<>();
		JSONArray jsonArray = json.getJSONArray("caseResultList");
		for (int i = 0; i < jsonArray.size(); i++) {
			caseResultList
					.add(new Gson().fromJson(jsonArray.getJSONObject(i).toString(), AutomationTestCaseResult.class));
		}
		return caseResultList;
	}

	private void reportPrefixHandle(AutomationTestResultDTO dto) {
		JsonReportOfEventDTO eventReport = dto.getReport();
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
