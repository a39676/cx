package demo.automationTest.controller;

import java.util.List;

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
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import demo.automationTest.pojo.result.InsertSearchingDemoEventResult;
import demo.automationTest.pojo.vo.TestReportSummaryVO;
import demo.automationTest.service.AutomationTestHomepageService;
import demo.common.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = AutoTestUrl.ROOT)
public class AutoTestDemoController extends CommonController {

	@Autowired
	private AutomationTestHomepageService atDemoService;

	@PostMapping(value = AutoTestUrl.LINK_TO_AT_HOME)
	public ModelAndView linkToATHome(HttpServletRequest request) {
		return atDemoService.linkToATHome(request);
	}

	@GetMapping(value = AutoTestUrl.INDEX)
	public ModelAndView index() {
		return atDemoService.index();
	}

	@PostMapping(value = AutoTestInteractionUrl.FIND_REPORTS_BY_CONDITION)
	@ResponseBody
	public List<TestReportSummaryVO> findReportsByCondition(@RequestBody FindTestEventPageByConditionDTO dto) {
		return atDemoService.findReportsByCondition(dto);
	}

	@GetMapping(value = AutoTestInteractionUrl.FIND_REPORT_BY_TEST_EVENT_ID)
	@ResponseBody
	public ModelAndView findReportByTestEventId(HttpServletRequest request,
			@RequestParam(value = "testEventId", defaultValue = "0", required = false) Long testEventId) {
		FindReportByTestEventIdDTO dto = new FindReportByTestEventIdDTO();
		dto.setTestEventId(testEventId);
		return atDemoService.findReportByTestEventId(request, dto);
	}

	@PostMapping(value = AutoTestInteractionUrl.INSERT_SEARCHING_DEMO_TEST_EVENT)
	@ResponseBody
	public InsertSearchingDemoEventResult insertSearchingDemoTestEvent(@RequestBody BingSearchInHomePageDTO dto,
			HttpServletRequest request) {
		return atDemoService.insertSearchingDemoTestEvent(dto, request);
	}

}
