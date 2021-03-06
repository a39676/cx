package demo.base.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.BlogViewConstant;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.BasePageService;
import demo.base.system.service.HostnameService;
import demo.base.user.pojo.type.SystemRolesType;
import demo.common.service.CommonService;

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
		
		HostnameType hostnameType = hostnameService.findHostnameType(request);
		
		ModelAndView view = new ModelAndView();

		String envName = systemConstantService.getEnvNameRefresh();
		
		if (hostnameType == null) {
			if(!"dev".equals(envName)) {
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
//			} else if (HostnameType.three.equals(hostnameType)) {
//				view = buildHomeViewForSeekingWork();
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
		ModelAndView view = new ModelAndView(BlogViewConstant.home);
		view.addObject("title", systemConstantService.getNormalWebSiteTitle());
		view.addObject("headerImg", "/static_resources/cleanBlog/img/nature-4607496_1920.jpg");
		view.addObject("subheading", systemConstantService.getNormalSubheading());
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
				v = new ModelAndView(BlogViewConstant.aboutEasy);
				v.addObject("email", systemConstantService.getEmailD());
				v.addObject("headerImg", "/static_resources/cleanBlog/img/nature-4607496_1920.jpg");
//			} else if (HostnameType.seek.equals(hostnameType)) {
//				v = new ModelAndView(BlogViewConstant.aboutSeek);
//				v.addObject("email", constantService.getValByName(SystemConstantStore.emailc));
//				v.addObject("headerImg", atDemoHeadImg);
			}
		}

		return v;
	}

}
