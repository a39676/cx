package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.scheduleClawing.hsbc.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.bbtOrder.hsbc.pojo.vo.RandomIdDataVO;
import demo.tool.bbtOrder.hsbc.service.HsbcService;
import demo.tool.bbtOrder.underWayMonthTest.service.UnderWayMonthTestService;

@RequestMapping(value = "/publicTool")
@Controller
public class PublicToolController extends CommonController {

	@Autowired
	private HsbcService hsbcService;
	
	@Autowired
	private UnderWayMonthTestService underWayMonthTestService;
	
	@GetMapping(value = "/hsbc/hsbcWechatPreregist_")
	public ModelAndView hsbcWechatPreregistView() {
		return hsbcService.hsbcWechatPreregistView();
	}
	
	@PostMapping(value = "/hsbc/getRandomIdData")
	@ResponseBody
	public RandomIdDataVO getRandomIdData() {
		return hsbcService.getRandomIdData();
	}
	
	@PostMapping(value = "/hsbc/hsbcWechatPreregist")
	@ResponseBody
	public CommonResult hsbcWechatPreregist(@RequestBody HsbcWechatPreregistDTO dto) {
		return hsbcService.hsbcWechatPreregist(dto);
	}
	
	@PostMapping(value = "/hsbc/getReportSummaryPage")
	public ModelAndView getReportSummaryPage(@RequestBody HsbcWechatPreregistDTO dto) {
		return hsbcService.getReportSummaryPage(dto);
	}
	
	@GetMapping(value = "/freeYourTime/monthTest")
	public ModelAndView monthTestView() {
		return underWayMonthTestService.monthTestView();
	}
	
	@PostMapping(value = "/freeYourTime/addMonthTest")
	@ResponseBody
	public CommonResult monthTestView(@RequestBody UnderWayMonthTestDTO dto) {
		return underWayMonthTestService.monthTest(dto);
	}
}
