package demo.automationTest.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import demo.automationTest.mapper.AutomatinTestReportMapper;
import demo.automationTest.pojo.po.AutomatinTestReport;
import demo.automationTest.service.AutomationTestResultReceiveService;
import demo.common.service.CommonService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;

@Service
public class AutomationTestResultReceiveServiceImpl extends CommonService
		implements AutomationTestResultReceiveService {

	@Autowired
	private AutomatinTestReportMapper reportMapper;
	@Autowired
	private AutomationTestConstantService constantService;
	
	@Override
	public void savingReport(AutomationTestResultDTO dto) {
		/*
		 * TODO
		 * 只处理了报告, 未处理其他数据 
		 */
		if(dto.getTestEventId() == null || StringUtils.isBlank(dto.getReportStr())) {
			return;
		}
		AutomatinTestReport newPO = new AutomatinTestReport();
		newPO.setId(snowFlake.getNextId());
		newPO.setTestEventId(dto.getTestEventId());
		
		String folderPath = getSavingFolder();
		if(folderPath == null) {
			return;
		}
		newPO.setReportPath(folderPath + File.separator + dto.getTestEventId() + ".json");
		
		reportMapper.insertSelective(newPO);
	}
	
	private String getSavingFolder() {
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
