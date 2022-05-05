package demo.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService2;
import demo.tool.bbtOrder.hsbc.service.HsbcService;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@GetMapping(value = "/t")
	@ResponseBody
	public String t() {
		return """
				{"testKey":"testValue"}
				""";
	}
	
	@GetMapping(value = "/t1")
	@ResponseBody
	public ModelAndView t1() {
		return new ModelAndView("testJSP/test");
	}

	@GetMapping(value = "/t2")
	@ResponseBody
	public String t2() {
		return "{\"k\":\"v\"}";
	}

	@Autowired
	private TestService2 testService;
	
	@GetMapping(value = "/t6")
	public ModelAndView getRole() {
		ModelAndView v = new ModelAndView("testJSP/test");
		List<String> roles = testService.getRoles();
		v.addObject("message", roles);
		return v;
	}
	
	@Autowired
	private HsbcService hsbcService;
	
	@GetMapping(value = "/t7")
	@ResponseBody
	public String t7() {
		HsbcWechatPreregistDTO dto = new HsbcWechatPreregistDTO();
		dto.setIdNumber("136602198001013003");
		dto.setPhoneNumber("17822222223");
		dto.setMainlandFlag(true);
		dto.setMainlandPhoneFlag(true);
		dto.setMainUrl("https://www.hkg2vl0830-cn.p2g.netd2.hsbc.com.hk/PublicContent/wechat/wechat_library/VTM/prd-branch/index.html#/");
		dto.setStaffId("44123708");
		
		hsbcService.sendHsbcWechatPreregistTask(dto);
		return "Done";
	}
}
