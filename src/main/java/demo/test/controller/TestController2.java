//package demo.test.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import demo.common.controller.CommonController;
//import demo.test.pojo.constant.TestUrl;
//import demo.test.service.TestService;
//
//@Controller
//@RequestMapping(value = { TestUrl.root2 })
//public class TestController2 extends CommonController {
//
////	@SuppressWarnings("unused")
//	@Autowired
//	private TestService testService;
//	
//	@GetMapping(value = "/get5MinData")
//	@ResponseBody
//	public String get5MinData() {
//		return testService.get5MinData();
//	}
//	
//	@GetMapping(value = "/createCacheData")
//	@ResponseBody
//	public String createCacheData() {
//		testService.createCacheData();
//		return "done";
//	}
//
//}
