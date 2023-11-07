package demo.base.system.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface BasePageService {

	ModelAndView baseRootHandlerCleanBlog(String vcode, HttpServletRequest request);

	ModelAndView aboutMeHandler(String vcode, HttpServletRequest request);

	ModelAndView baseRootHandlerV4(String vcode, HttpServletRequest request);

}
