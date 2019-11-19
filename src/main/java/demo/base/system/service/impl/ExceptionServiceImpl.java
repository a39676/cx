package demo.base.system.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.service.ExceptionService;
import demo.baseCommon.service.CommonService;

@Service
public class ExceptionServiceImpl extends CommonService implements ExceptionService {

	private static final String[] description = { "神奇", "野生", "迷幻", "抽象", "清奇", "脱俗", "清新", "艳丽"};

	private int getRan() {
		return ThreadLocalRandom.current().nextInt(0, description.length - 1);
	}
	
	@Override
	public ModelAndView handleCommonException(HttpServletRequest request, boolean debugStatus, Exception e) {
		log.error(e.toString());
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(debugStatus) {
			view.addObject("message", e.toString());
		} else {
			view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		}
		view.addObject("urlRedirect", findHostNameFromRequst(request));

		e.printStackTrace();
		return view;
	}

	@Override
	public ModelAndView handleCommonException(HttpServletRequest request, boolean debugStatus) {
		log.error("_");
		ModelAndView view = new ModelAndView("baseJSP/errorCustom");
		if(debugStatus) {
			view.addObject("message");
		} else {
			view.addObject("message", "很抱歉,居然出现了" + description[getRan()] + "的异常");
		}
		view.addObject("urlRedirect", findHostNameFromRequst(request));

		return view;
	}
}
