package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.BlogViewConstant;
import demo.base.system.service.BasePageService;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.service.CommonService;
import demo.util.BaseUtilCustom;

@Service
public class BasePageServiceImpl extends CommonService implements BasePageService {

	@Autowired
	private BaseUtilCustom baseUtilCustom;

	@Autowired
	private SystemConstantService systemConstantService;

	
	@Override
	public ModelAndView baseRootHandlerV3(String vcode, HttpServletRequest request) {

		if(StringUtils.isBlank(vcode)) {
			visitDataService.insertVisitData(request);
		} else {
			visitDataService.insertVisitData(request, "?vcode=" + vcode);
		}
		visitDataService.addVisitCounting(request);
		
		Long visitCount = visitDataService.getVisitCount();
		
		ModelAndView view = new ModelAndView();
		String hostName = findHostNameFromRequst(request);
		view.setViewName(BlogViewConstant.home);
//		view.setViewName(BaseViewConstant.homeV3);

		view.addObject("title", systemConstantService.getValByName(SystemConstantStore.webSiteTitle));
		view.addObject("visitCount", visitCount);

		String envName = systemConstantService.getValByName(SystemConstantStore.envName, true);
		
		if(!"dev".equals(envName)) {
			if (StringUtils.isBlank(hostName)) {
				view.setViewName(BaseViewConstant.empty);
				return view;
			}
		}

		if (hostName.contains(systemConstantService.getValByName(SystemConstantStore.hostName2))) {
			if(!"1".contentEquals(systemConstantService.getValByName(SystemConstantStore.jobing))) {
				view.setViewName(BaseViewConstant.empty);
				return view;
			}
			view.addObject("isJobView", "true");
		}

		List<String> roles = baseUtilCustom.getRoles();
		if (roles != null && roles.size() > 0 && roles.contains(RolesType.ROLE_USER.getName())) {
			HashMap<String, Object> authDetailMap = baseUtilCustom.getAuthDetail();
			if (authDetailMap != null && authDetailMap.containsKey("nickName")) {
				view.addObject("nickName", authDetailMap.get("nickName"));
			}
		} 

		return view;
	}
	
	@Override
	public ModelAndView aboutMeHandler(String vcode, HttpServletRequest request) {
		return new ModelAndView(BlogViewConstant.about);
	}

}
