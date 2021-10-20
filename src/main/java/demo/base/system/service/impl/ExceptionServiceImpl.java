package demo.base.system.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.ExceptionService;
import demo.common.service.CommonService;

@Service
public class ExceptionServiceImpl extends CommonService implements ExceptionService {

	private static final String[] description = { "神奇", "野生", "迷幻", "抽象", "清奇", "脱俗", "清新", "艳丽"};

	private int getRan() {
		return ThreadLocalRandom.current().nextInt(0, description.length - 1);
	}
	
	@Override
	public ModelAndView handleCommonException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		log.error("Catch EXCEPTION: " + request.getServerName() + "/" + request.getRequestURI());
		visitDataService.insertVisitData(request, "catch EXCEPTION");
		
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(systemConstantService.getIsDebuging()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		}
		view.addObject("urlRedirect", hostnameService.findHostNameFromRequst(request));

		e.printStackTrace();
		return view;
	}
	
	@Override
	public ModelAndView handle404Exception(HttpServletRequest request) {
		log.error("Http 404: " + request.getServerName() + "/" + request.getRequestURI());
		visitDataService.insertVisitData(request, "catch 404 exception");
		
		ModelAndView view = new ModelAndView();
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if(HostnameType.zhang3.equals(hostnameType)) {
			view.setViewName(BaseViewConstant.normal404);
		} else if(HostnameType.dtro.equals(hostnameType)) {
			view.setViewName(BaseViewConstant.normal404);
		} else if(isDev()){
			view.setViewName(BaseViewConstant.normal404);
		} else {
			view.setViewName(BaseViewConstant.normal404);
		}

		return view;
	}
	
	@Override
	public ModelAndView handle403CommonException(HttpServletRequest request) {
		log.error("Http 403: " + request.getServerName() + "/" + request.getRequestURI());
		visitDataService.insertVisitData(request, "catch 403 exception");
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		view.addObject("urlRedirect", hostnameService.findHostNameFromRequst(request));

		return view;
	}
	
	@Override
	public ModelAndView handleCommonException(HttpServletRequest request) {
		log.error("Catch EXCEPTION: " + request.getServerName() + "/" + request.getRequestURI());
		visitDataService.insertVisitData(request, "catch EXCEPTION");
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		view.addObject("urlRedirect", hostnameService.findHostNameFromRequst(request));
		
		return view;
	}
}
