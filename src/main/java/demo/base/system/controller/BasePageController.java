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
import demo.baseCommon.controller.CommonController;
import demo.finance.account_info.pojo.constant.AccountUrl;

@Controller
public class BasePageController extends CommonController {
	
//	@Autowired
//	private BaseUtilCustom baseUtilCustom;
//	@Autowired
//	private AdminService adminService;
//	@Autowired
//	private ArticleController articleController;
//	@Autowired
//	private SystemConstantService systemConstantService;
	@Autowired
	private BasePageService basePageService;
	
	@GetMapping(value = { BaseUrl.baseRoot })
	public ModelAndView welcomePageV2(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			HttpServletRequest request) {
		ModelAndView view = basePageService.baseRootHandlerV3(vcode, request);
		return view;
	}
	
	@GetMapping(value = { BaseUrl.aboutMe })
	public ModelAndView aboutMe(@RequestParam(value = "vcode", defaultValue = "") String vcode,
			HttpServletRequest request) {
		ModelAndView view = basePageService.aboutMeHandler(vcode, request);
		return view;
	}
	
	@GetMapping(value = { AccountUrl.financeclear })
	public ModelAndView financeclearHome() {
		ModelAndView view = new ModelAndView();
		view.addObject("title", "financeclear");
		view.setViewName(BaseViewConstant.financeclearFrame);
		return view;

	}
	
	@GetMapping(value = "/ea404")
	public ModelAndView ea404() {
		return new ModelAndView(BaseViewConstant.normal404);
	}
	
	@GetMapping(value = "/seek404")
	public ModelAndView seek404() {
		return new ModelAndView(BaseViewConstant.seekingJob404);
	}
}