package demo.base.system.service;

import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

public interface BasePageService {

	ModelAndView baseRootHandlerV3(String vcode, HttpServletRequest request);

	ModelAndView aboutMeHandler(String vcode, HttpServletRequest request);

	ModelAndView baseRootHandlerV4(String vcode, HttpServletRequest request);

}
