package demo.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;

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


}
