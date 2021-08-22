package demo.automationTest.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import demo.automationTest.mapper.TestEventMapper;
import demo.automationTest.pojo.po.TestEvent;
import demo.automationTest.service.AutomationTestResultReceiveService;
import demo.common.service.CommonService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;

@Service
public class AutomationTestResultReceiveServiceImpl extends CommonService
		implements AutomationTestResultReceiveService {

	@Autowired
	private AutomationTestConstantService constantService;
	@Autowired
	private TestEventMapper eventMapper;
	
	@Override
	public void savingReport(AutomationTestResultDTO dto) {
		if(dto.getTestEventId() == null) {
			return;
		}
		
		TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
		
		String folderPath = getAutomationTestReportSavingFolder();
		if(folderPath == null) {
			return;
		}
		po.setReportPath(folderPath + File.separator + dto.getTestEventId() + ".json");
		
		po.setIsPass(true);
		for(AutomationTestCaseResult subResult : dto.getCaseResultList()) {
			if(!AutomationTestFlowResultType.PASS.equals(subResult.getResultType())) {
				po.setIsPass(false);
				break;
			}
		}
		
		po.setRemark(dto.getRemark());
		po.setStartTime(dto.getStartTime());
		po.setEndTime(dto.getEndTime());
		eventMapper.updateByPrimaryKeySelective(po);
	}
	
	private String getAutomationTestReportSavingFolder() {
		String path = constantService.getReportStorePrefixPath() + File.separator + localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.dateFormatNoSymbol);
		File folder = new File(path);
		if(!folder.exists() || !folder.isDirectory()) {
			if(!folder.mkdirs()) {
				return null;
			}
		}
		return path;
	}
}
