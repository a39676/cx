package demo.interaction.autoTest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface AutoTestDemoPageService {

	ModelAndView linkToATHome(HttpServletRequest request);

}
