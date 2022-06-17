package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.dto.HeShaBiCaoWechatPreregistDTO;
import autoTest.testEvent.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.bbtOrder.heShaBiCao.service.HeShaBiCaoService;
import demo.tool.bbtOrder.underWayMonthTest.service.UnderWayMonthTestService;

@RequestMapping(value = "/publicTool")
@Controller
public class PublicToolController extends CommonController {

	@Autowired
	private HeShaBiCaoService heShaBiCaoService;
	
	@Autowired
	private UnderWayMonthTestService underWayMonthTestService;
	
	@GetMapping(value = "/heShaBiCao/heShaBiCaoWechatPreregist")
	public ModelAndView heShaBiCaoWechatPreregistView() {
		return heShaBiCaoService.heShaBiCaoWechatPreregistView();
	}
	
	@PostMapping(value = "/heShaBiCao/heShaBiCaoWechatPreregist")
	@ResponseBody
	public CommonResult heShaBiCaoWechatPreregist(@RequestBody HeShaBiCaoWechatPreregistDTO dto) {
		return heShaBiCaoService.heShaBiCaoWechatPreregist(dto);
	}
	
	@PostMapping(value = "/heShaBiCao/getReportSummaryPage")
	public ModelAndView getReportSummaryPage(@RequestBody HeShaBiCaoWechatPreregistDTO dto) {
		return heShaBiCaoService.getReportSummaryPage(dto);
	}
	
	@GetMapping(value = "/freeYourTime/monthTest")
	public ModelAndView monthTestView() {
		return underWayMonthTestService.monthTestView();
	}
	
	@PostMapping(value = "/freeYourTime/addMonthTest")
	@ResponseBody
	public CommonResult heShaBiCaoWechatPreregistView(@RequestBody UnderWayMonthTestDTO dto) {
		return underWayMonthTestService.monthTest(dto);
	}
}
