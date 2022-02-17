	package demo.base.system.controller;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import demo.base.system.service.ExceptionService;
import demo.common.controller.CommonController;

@ControllerAdvice
public class ExceptionController extends CommonController {
	
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

	@ExceptionHandler({ SQLException.class })
	public ModelAndView handleSQLException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ModelAndView hanedleRuntimeException(HttpServletRequest request, Exception e) {
		return exceptionService.handleCommonException(request, e);
	}
	
	@ExceptionHandler({SQLSyntaxErrorException.class, BadSqlGrammarException.class})
    public void handleSQLErrorException(HttpServletRequest request, Exception e)   {
        exceptionService.handleSQLErrorException(request, e);
    }
	
	@ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleError404_1(HttpServletRequest request, Exception e)   {
        return exceptionService.handle404Exception(request);
    }
	
	@ExceptionHandler(InaccessibleObjectException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleInaccessibleObjectException(HttpServletRequest request, Exception e)   {
        return exceptionService.handle503Exception(request, e);
    }
	

	
//	@ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ModelAndView handleError404_2(HttpServletRequest request, Exception e)   {
//        return exceptionService.handle404Exception(request, e);
//    }

}
