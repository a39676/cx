package demo.autoTest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.autoTest.pojo.constant.AutoTestUrl;
import demo.autoTest.service.AutoTestDemoPageService;
import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping(value = AutoTestUrl.root)
public class AutoTestDemoController extends CommonController {

	@Autowired
	private AutoTestDemoPageService pageService;
	
	@PostMapping(value = AutoTestUrl.linkToATHome)
	public ModelAndView linkToATHome(HttpServletRequest request) {
		return pageService.linkToATHome(request);
	}
}
