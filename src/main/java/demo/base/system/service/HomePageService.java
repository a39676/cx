package demo.base.system.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface HomePageService {

	ModelAndView baseRootHandlerV3(String vcode, HttpServletRequest request);

}
