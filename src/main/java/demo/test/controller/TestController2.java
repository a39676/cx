package demo.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.SnowFlake;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@Autowired
	private SnowFlake snowFlake;
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;
	@Autowired
	private TestService testService;

	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}
	
	@GetMapping(value = "/about")
	public ModelAndView about() {
		return new ModelAndView("cleanBlogJSP/about");
	}
	
	@GetMapping(value = "/tw")
	public ModelAndView tw() throws Exception {
		ModelAndView v = new ModelAndView("testJSP/test01");
		v.addObject("message", "<img src=\"http://wttr.in/Guangzhou.png\">");
		return v;
	}
	
	@GetMapping(value = "/testMyFindHostNameFromRequst")
	@ResponseBody
	public String testMyFindHostNameFromRequst(HttpServletRequest request) {
		return testService.testMyFindHostNameFromRequst(request);
	}
	
	
}
