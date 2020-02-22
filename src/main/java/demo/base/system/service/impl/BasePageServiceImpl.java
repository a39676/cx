package demo.base.system.service.impl;

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
import demo.base.user.pojo.type.SystemRolesType;
import demo.baseCommon.service.CommonService;

@Service
public class BasePageServiceImpl extends CommonService implements BasePageService {

	private static String atDemoHeadImg = "/static_resources/cleanBlog/img/post-sample-image.jpg";
	
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
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		
		ModelAndView view = new ModelAndView();

		String envName = constantService.getValByName(SystemConstantStore.envName, true);
		
		if (hostnameType == null) {
			if(!"dev".equals(envName)) {
				if(hostnameType == null) {
					view.setViewName(BaseViewConstant.empty);
					return view;
				}
			} else {
				view = buildHomeViewForEA();
			}
		} else {
			if (HostnameType.ea.equals(hostnameType) || HostnameType.haven.equals(hostnameType)) {
				view = buildHomeViewForEA();
			} else if (HostnameType.seek.equals(hostnameType)) {
				view = buildHomeViewForSeek();
			}
		}

		List<String> roles = baseUtilCustom.getRoles();
		if (roles != null && roles.size() > 0 && roles.contains(SystemRolesType.ROLE_USER.getName())) {
			view.addObject("nickName", baseUtilCustom.getUserPrincipal().getNickName());
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
		view.addObject("headerImg", atDemoHeadImg);
		view.addObject("subheading", "Bugs forced the development in a certain sense");
		
		return view;
	}

	@Override
	public ModelAndView aboutMeHandler(String vcode, HttpServletRequest request) {
		ModelAndView v = null;

		HostnameType hostnameType = hostnameService.findHostnameType(request);
		if (hostnameType != null) {
			if (HostnameType.ea.equals(hostnameType)) {
				v = new ModelAndView(BlogViewConstant.aboutEasy);
				v.addObject("email", constantService.getValByName(SystemConstantStore.emaild));
				v.addObject("headerImg", "/static_resources/cleanBlog/img/nature-4607496_1920.jpg");
			} else if (HostnameType.seek.equals(hostnameType)) {
				v = new ModelAndView(BlogViewConstant.aboutSeek);
				v.addObject("email", constantService.getValByName(SystemConstantStore.emailc));
				v.addObject("headerImg", atDemoHeadImg);
			}
		}

		return v;
	}

}
