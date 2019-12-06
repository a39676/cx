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

import autoTest.jsonReport.pojo.constant.JsonReportInteractionUrl;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import demo.baseCommon.controller.CommonController;
import demo.interaction.autoTest.pojo.constant.AutoTestUrl;
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
	public ModelAndView index() {
		return atDemoService.index();
	}
	
	@PostMapping(value = JsonReportInteractionUrl.findReportsByCondition)
	@ResponseBody
	public String findReportsByCondition(@RequestBody FindTestEventPageByConditionDTO dto) {
		return atDemoService.findReportsByCondition(dto);
	}
	
	@GetMapping(value = JsonReportInteractionUrl.findReportByTestEventId)
	@ResponseBody
	public ModelAndView findReportByTestEventId(@RequestParam(value = "testEventId", defaultValue = "0", required = false) Long testEventId) {
		FindReportByTestEventIdDTO dto = new FindReportByTestEventIdDTO();
		dto.setTestEventId(testEventId);
		return atDemoService.findReportByTestEventId(dto);
	}
}
