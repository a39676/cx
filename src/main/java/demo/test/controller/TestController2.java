package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

//	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;


	@GetMapping(value = "/addCache")
	@ResponseBody
	public String addCache() {
		testService.addCache();
		return "done";
	}
	
	@GetMapping(value = "/getCache")
	@ResponseBody
	public String getCache(@RequestParam(value = "coinType")Integer coinType) {
		return testService.getCache(coinType);
	}
	
	@GetMapping(value = "/getFake1MinData")
	@ResponseBody
	public String getFake1MinData() {
		return testService.getFake1MinData();
	}
	
	@GetMapping(value = "/getFake5MinData")
	@ResponseBody
	public String getFake5MinData() {
		return testService.getFake5MinData();
	}
}
