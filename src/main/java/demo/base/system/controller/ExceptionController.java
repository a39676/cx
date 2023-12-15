package demo.base.system.controller;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.ExceptionService;
import demo.common.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends CommonController {

//	@Autowired
//	private MailService mailService;

	@Autowired
	protected ExceptionService exceptionService;

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public CommonResult handleException(HttpServletRequest request, Exception e, String message) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ IOException.class })
	@ResponseBody
	public CommonResult handleIOException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ SQLException.class })
	@ResponseBody
	public CommonResult handleSQLException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ RuntimeException.class })
	@ResponseBody
	public CommonResult hanedleRuntimeException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ SQLSyntaxErrorException.class, BadSqlGrammarException.class })
	public void handleSQLErrorException(HttpServletRequest request, Exception e) {
		exceptionService.handleSQLErrorException(request, e);
	}

	@ExceptionHandler(RequestRejectedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView handleRequestRejectedException(HttpServletRequest request, Exception e) {
		exceptionService.handleRequestRejectedException(request, e);
		return exceptionService.handle404Exception(request);
	}

	@ExceptionHandler({ NoHandlerFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView handleError404_1(HttpServletRequest request, Exception e) {
		return exceptionService.handle404Exception(request);
	}

	@ExceptionHandler(InaccessibleObjectException.class)
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public ModelAndView handleInaccessibleObjectException(HttpServletRequest request, Exception e) {
		return exceptionService.handle503Exception(request, e);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ResponseBody
	public CommonResult haneleHttpMediaTypeNotSupportedException(HttpServletRequest request, Exception e) {
		return exceptionService.handle415Exception(request);
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public CommonResult handleUnrecognizedPropertyException(HttpServletRequest request,
			UnrecognizedPropertyException e) {
		return exceptionService.hadleUnrecognizedPropertyException(request, e);
	}

//	@ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ModelAndView handleError404_2(HttpServletRequest request, Exception e)   {
//        return exceptionService.handle404Exception(request, e);
//    }

}
