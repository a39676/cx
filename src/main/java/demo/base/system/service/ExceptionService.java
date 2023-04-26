package demo.base.system.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import auxiliaryCommon.pojo.result.CommonResult;

public interface ExceptionService {

	CommonResult handleCommonException(HttpServletRequest request, Exception e);

	ModelAndView handle404Exception(HttpServletRequest request);

	ModelAndView handle403CommonException(HttpServletRequest request);

	ModelAndView handle503Exception(HttpServletRequest request, Exception e);

	void handleSQLErrorException(HttpServletRequest request, Exception e);

	void handleRequestRejectedException(HttpServletRequest request, Exception e);

	CommonResult handle415Exception(HttpServletRequest request);

	CommonResult hadleUnrecognizedPropertyException(HttpServletRequest request, UnrecognizedPropertyException e);

}
