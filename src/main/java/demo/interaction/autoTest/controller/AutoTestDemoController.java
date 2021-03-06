package demo.interaction.autoTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.constant.AutoTestInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.pojo.constant.AutoTestUrl;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import demo.common.controller.CommonController;
import demo.interaction.autoTest.service.AutoTestDemoService;

@Controller
@RequestMapping(value = AutoTestUrl.root)
public class AutoTestDemoController extends CommonController {

	@Autowired
	private AutoTestDemoService atDemoService;
	
	@PostMapping(value = AutoTestUrl.linkToATHome)
	public ModelAndView linkToATHome(HttpServletRequest request) {
		return atDemoService.linkToATHome(request);
	}
	
	@GetMapping(value = AutoTestUrl.index)
	public ModelAndView index(HttpServletRequest request) {
		return atDemoService.index(request);
	}
	
	@PostMapping(value = AutoTestInteractionUrl.FIND_REPORTS_BY_CONDITION)
	@ResponseBody
	public String findReportsByCondition(HttpServletRequest request, @RequestBody FindTestEventPageByConditionDTO dto) {
		return atDemoService.findReportsByCondition(request, dto);
	}
	
	@GetMapping(value = AutoTestInteractionUrl.FIND_REPORT_BY_TEST_EVENT_ID)
	@ResponseBody
	public ModelAndView findReportByTestEventId(HttpServletRequest request, @RequestParam(value = "testEventId", defaultValue = "0", required = false) Long testEventId) {
		FindReportByTestEventIdDTO dto = new FindReportByTestEventIdDTO();
		dto.setTestEventId(testEventId);
		return atDemoService.findReportByTestEventId(request, dto);
	}
	
	@PostMapping(value = AutoTestInteractionUrl.INSERT_SEARCHING_DEMO_TEST_EVENT)
	@ResponseBody
	public InsertSearchingDemoEventResult insertSearchingDemoTestEvent(@RequestBody InsertSearchingDemoTestEventDTO dto, HttpServletRequest request) {
		return atDemoService.insertSearchingDemoTestEvent(dto, request);
	}
}
