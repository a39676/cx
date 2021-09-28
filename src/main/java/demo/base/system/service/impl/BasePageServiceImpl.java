package demo.base.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.pojo.constant.BaseStaticResourcesUrl;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.BlogViewConstant;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.BasePageService;
import demo.base.user.pojo.type.SystemRolesType;
import demo.common.service.CommonService;

@Service
public class BasePageServiceImpl extends CommonService implements BasePageService {

	@Override
	public ModelAndView baseRootHandlerV3(String vcode, HttpServletRequest request) {

		if(StringUtils.isBlank(vcode)) {
			visitDataService.insertVisitData(request);
		} else {
			visitDataService.insertVisitData(request, "?vcode=" + vcode);
		}
		visitDataService.addVisitCounting(request);
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		
		ModelAndView view = new ModelAndView();

		
		if (hostnameType == null) {
			if(!isDev()) {
				if(hostnameType == null) {
					view.setViewName(BaseViewConstant.empty);
					return view;
				}
			} else {
				view = buildHomeViewForNormal();
			}
		} else {
			if (HostnameType.zhang3.equals(hostnameType)) {
				view = buildHomeViewForNormal();
			} else if (HostnameType.dtro.equals(hostnameType)) {
				view = buildHomeViewForFake();
			}
		}

		List<String> roles = baseUtilCustom.getRoles();
		if (roles != null && roles.size() > 0 && roles.contains(SystemRolesType.ROLE_USER.getName())) {
			view.addObject("nickName", baseUtilCustom.getUserPrincipal().getNickName());
		}
		
		view.addObject("isHomePage", "true");
		
		return view;
	}
	
	private ModelAndView buildHomeViewForNormal() {
		ModelAndView view = new ModelAndView(BlogViewConstant.HOME);
		view.addObject("title", systemConstantService.getNormalWebSiteTitle());
		view.addObject("headerImg", BaseStaticResourcesUrl.IMG_YELLOW_GRASS_LAND);
		view.addObject("subheading", systemConstantService.getNormalSubheading());
		Long visitCount = visitDataService.getVisitCount();
		view.addObject("visitCount", visitCount);
		
		return view;
	}
	
	private ModelAndView buildHomeViewForFake() {
		ModelAndView view = new ModelAndView(BlogViewConstant.HOME);
		view.addObject("title", systemConstantService.getWebSiteTitle2());
		view.addObject("headerImg", BaseStaticResourcesUrl.IMG_CAT);
		view.addObject("subheading", systemConstantService.getSubheading2());
		Long visitCount = visitDataService.getVisitCount();
		view.addObject("visitCount", visitCount);
		
		return view;
	}
	
	@Override
	public ModelAndView aboutMeHandler(String vcode, HttpServletRequest request) {
		ModelAndView v = null;

		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if (hostnameType != null) {
			if (HostnameType.zhang3.equals(hostnameType)) {
				v = new ModelAndView(BlogViewConstant.ABOUT_ZHANG);
				v.addObject("email", systemConstantService.getEmailD());
				v.addObject("headerImg", BaseStaticResourcesUrl.IMG_YELLOW_GRASS_LAND);
			} else if (HostnameType.dtro.equals(hostnameType)) {
				v = new ModelAndView(BlogViewConstant.ABOUT_DTRO);
				v.addObject("email", systemConstantService.getEmailC());
				v.addObject("headerImg", BaseStaticResourcesUrl.IMG_CAT);
				
			}
		}

		return v;
	}

}
