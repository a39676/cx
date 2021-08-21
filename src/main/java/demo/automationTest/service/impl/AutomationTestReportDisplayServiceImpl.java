package demo.automationTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.automationTest.mapper.AutomatinTestReportMapper;
import demo.automationTest.service.AutomationTestReportDisplayService;
import demo.common.service.CommonService;

@Service
public class AutomationTestReportDisplayServiceImpl extends CommonService
		implements AutomationTestReportDisplayService {

	@Autowired
	private AutomatinTestReportMapper reportMapper;
	
	
}
