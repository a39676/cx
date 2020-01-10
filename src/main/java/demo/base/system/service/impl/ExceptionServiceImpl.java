package demo.base.system.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.DebugStatusConstant;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.ExceptionService;
import demo.base.system.service.HostnameService;
import demo.baseCommon.service.CommonService;

@Service
public class ExceptionServiceImpl extends CommonService implements ExceptionService {

	private static final String[] description = { "神奇", "野生", "迷幻", "抽象", "清奇", "脱俗", "清新", "艳丽"};

	private int getRan() {
		return ThreadLocalRandom.current().nextInt(0, description.length - 1);
	}
	
	@Autowired
	private HostnameService hostnameService;
	
	@Override
	public ModelAndView handleCommonException(HttpServletRequest request, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(findDebugStatus()) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		}
		view.addObject("urlRedirect", findHostNameFromRequst(request));

		e.printStackTrace();
		return view;
	}
	
	@Override
	public ModelAndView handle404Exception(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		
		HostnameType hostnameType = hostnameService.findHostname(request);
		if(HostnameType.ea.equals(hostnameType)) {
			view.setViewName(BaseViewConstant.ea404);
		} else if(HostnameType.seek.equals(hostnameType)) {
			view.setViewName(BaseViewConstant.seek404);
		} else if("dev".equals(constantService.getValByName(SystemConstantStore.envName, true))){
			view.setViewName(BaseViewConstant.ea404);
		} else {
			view.setViewName(BaseViewConstant.empty);
		}

		return view;
	}

	@Override
	public ModelAndView handleCommonException(HttpServletRequest request) {
		log.error("_");
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		view.addObject("urlRedirect", findHostNameFromRequst(request));

		return view;
	}
	
	private boolean findDebugStatus() {
		String debugStatusStr = constantService.getValByName(SystemConstantStore.debugStatus);
		if(DebugStatusConstant.debuging.equals(debugStatusStr)) {
			return true;
		} else {
			return false;
		}
	}
}
