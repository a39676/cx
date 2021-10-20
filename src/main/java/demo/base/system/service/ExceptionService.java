package demo.base.system.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface ExceptionService {

	ModelAndView handleCommonException(HttpServletRequest request, Exception e);

	ModelAndView handleCommonException(HttpServletRequest request);

	ModelAndView handle404Exception(HttpServletRequest request);

	ModelAndView handle403CommonException(HttpServletRequest request);

}
