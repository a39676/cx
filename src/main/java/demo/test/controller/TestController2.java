package demo.test.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.jsonReport.pojo.constant.AutoTestInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import demo.common.controller.CommonController;
import demo.interaction.autoTest.service.AutoTestDemoService;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	
	@Autowired
	private AutoTestDemoService atDemoService;
	
	@PostMapping(value = AutoTestInteractionUrl.FIND_REPORTS_BY_CONDITION)
	@ResponseBody
	public String findReportsByCondition(HttpServletRequest request) {
		FindTestEventPageByConditionDTO dto = new FindTestEventPageByConditionDTO();
		dto.setCreateStartTime(LocalDateTime.now().minusMonths(6));
		dto.setCreateEndTime(LocalDateTime.now());
		dto.setRunTimeStartTime(LocalDateTime.now().minusMonths(6));
		dto.setRunTimeEndTime(LocalDateTime.now());
		return atDemoService.findReportsByCondition(request, dto);
	}

}
