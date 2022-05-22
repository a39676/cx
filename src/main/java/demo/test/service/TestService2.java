package demo.test.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.common.service.CommonService;

@Service
public class TestService2 extends CommonService {

	public ModelAndView testView() {
		return new ModelAndView("testJSP/test");
	}

}
