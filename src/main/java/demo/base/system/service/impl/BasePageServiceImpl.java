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
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.BasePageService;
import demo.base.system.service.HostnameService;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.service.CommonService;

@Service
public class BasePageServiceImpl extends CommonService implements BasePageService {

	@Autowired
	private HostnameService hostnameService;

	@Override
	public ModelAndView baseRootHandlerV3(String vcode, HttpServletRequest request) {

		if(StringUtils.isBlank(vcode)) {
			visitDataService.insertVisitData(request);
		} else {
			visitDataService.insertVisitData(request, "?vcode=" + vcode);
		}
		visitDataService.addVisitCounting(request);
		
		HostnameType hostnameType = hostnameService.findHostname(request);
		
		ModelAndView view = new ModelAndView();

		String envName = constantService.getValByName(SystemConstantStore.envName, true);
		
		if(!"dev".equals(envName)) {
			if(hostnameType == null) {
				view.setViewName(BaseViewConstant.empty);
				return view;
			}
		} else {
			view = buildHomeViewForEA();
		}
		
		if (hostnameType != null) {
			if (HostnameType.ea.equals(hostnameType)) {
				view = buildHomeViewForEA();
			} else if (HostnameType.seek.equals(hostnameType)) {
				view = buildHomeViewForSeek();
			}
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
	
	private ModelAndView buildHomeViewForEA() {
		ModelAndView view = new ModelAndView(BlogViewConstant.home);
		view.addObject("title", constantService.getValByName(SystemConstantStore.eaWebSiteTitle));
		view.addObject("headerImg", "/static_resources/cleanBlog/img/nature-4607496_1920.jpg");
		view.addObject("subheading", constantService.getValByName(SystemConstantStore.eaSubheading));
		Long visitCount = visitDataService.getVisitCount();
		view.addObject("visitCount", visitCount);
		
		return view;
	}
	
	private ModelAndView buildHomeViewForSeek() {
		ModelAndView view = new ModelAndView();
		if(!"1".contentEquals(constantService.getValByName(SystemConstantStore.jobing))) {
			view.setViewName(BaseViewConstant.empty);
			return view;
		}
		
		view.setViewName(BlogViewConstant.home);
		view.addObject("title", constantService.getValByName(SystemConstantStore.seekWebSiteTitle));
		view.addObject("headerImg", "/static_resources/cleanBlog/img/post-sample-image.jpg");
		view.addObject("subheading", "Bugs forced the development in a certain sense");
		
		return view;
	}

	@Override
	public ModelAndView aboutMeHandler(String vcode, HttpServletRequest request) {
		ModelAndView v = new ModelAndView(BlogViewConstant.about);

		HostnameType hostnameType = hostnameService.findHostname(request);
		if (hostnameType != null) {
			if (HostnameType.ea.equals(hostnameType)) {
				v.addObject("email", "davenchan12546@gmail.com");
			} else if (HostnameType.seek.equals(hostnameType)) {
				v.addObject("email", "chan189@aliyun.com");
			}
		}

		return v;
	}

}
