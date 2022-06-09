package demo.base.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.service.BasePageService;
import demo.common.controller.CommonController;

@Controller
public class BasePageController extends CommonController {
	
	@Autowired
	private BasePageService basePageService;
	
//	@GetMapping(value = { BaseUrl.ROOT4 })
//	public ModelAndView welcomePageV2(@RequestParam(value = "vcode", defaultValue = "") String vcode,
//			HttpServletRequest request) {
//		ModelAndView view = basePageService.baseRootHandlerV3(vcode, request);
//		return view;
//	}
	
	@GetMapping(value = { BaseUrl.ROOT })
	public ModelAndView welcomePageV4(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			HttpServletRequest request) {
		ModelAndView view = basePageService.baseRootHandlerV4(vcode, request);
		return view;
	}
	
	@GetMapping(value = { BaseUrl.ABOUT_ME })
	public ModelAndView aboutMe(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			HttpServletRequest request) {
		ModelAndView view = basePageService.aboutMeHandler(vcode, request);
		return view;
	}
	
	@GetMapping(value = "/ea404")
	public ModelAndView ea404() {
		return new ModelAndView(BaseViewConstant.normal404);
	}
	
}