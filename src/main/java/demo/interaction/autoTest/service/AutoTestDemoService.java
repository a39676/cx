package demo.interaction.autoTest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;

public interface AutoTestDemoService {

	ModelAndView linkToATHome(HttpServletRequest request);

	ModelAndView index();

	String findReportsByCondition(FindTestEventPageByConditionDTO dto);

	ModelAndView findReportByTestEventId(FindReportByTestEventIdDTO dto);

}
