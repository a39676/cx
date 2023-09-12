package demo.base.system.service;

import org.springframework.web.servlet.ModelAndView;

public interface BasePageService {

	ModelAndView baseRootHandlerCleanBlog(String vcode);

	ModelAndView aboutMeHandler(String vcode);

	ModelAndView baseRootHandlerV4(String vcode);

	ModelAndView baseRootHandler3G(String vcode);

}
