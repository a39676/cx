package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
}
