package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.bbtOrder.hsbc.service.HsbcService;

@RequestMapping(value = "/publicTool")
@Controller
public class PublicToolController extends CommonController {

	@Autowired
	private HsbcService hsbcService;
	
	@GetMapping(value = "/hsbc/hsbcWechatPreregist")
	public ModelAndView hsbcWechatPreregistView() {
		return hsbcService.hsbcWechatPreregistView();
	}
	
	@PostMapping(value = "/hsbc/hsbcWechatPreregist")
	@ResponseBody
	public CommonResult hsbcWechatPreregist(@RequestBody HsbcWechatPreregistDTO dto) {
		return hsbcService.hsbcWechatPreregist(dto);
	}
}
