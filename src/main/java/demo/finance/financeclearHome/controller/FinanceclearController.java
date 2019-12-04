package demo.finance.financeclearHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;

@Controller
@RequestMapping(value = "/financeclear")
public class FinanceclearController extends CommonController {
	
	@GetMapping(value = "/")
	public ModelAndView financeclear() {
		return new ModelAndView("baseElementJSP/financeclearFrame");
	}
}
