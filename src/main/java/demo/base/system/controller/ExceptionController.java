package demo.base.system.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import demo.base.system.service.ExceptionService;
import demo.baseCommon.controller.CommonController;

@ControllerAdvice
public class ExceptionController extends CommonController implements HandlerExceptionResolver {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

//	@Autowired
//	private MailService mailService;

	@Autowired
	protected ExceptionService exceptionService;

	@ExceptionHandler({ Exception.class })
	public ModelAndView handleException(HttpServletRequest request, Exception e, String message) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ IOException.class })
	public ModelAndView handleIOException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNoHandlerFoundException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ SQLException.class })
	public ModelAndView handleSQLException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ModelAndView hanedleRuntimeException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}
	

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		log.error(ex.toString());
		try {

			if (response.getStatus() == 500) {
				return handleException(request, ex, "500");
			} else {
				return handleException(request, ex, "???");
			}
		} catch (Exception handlerException) {
			
		}
		return null;
	}

}
