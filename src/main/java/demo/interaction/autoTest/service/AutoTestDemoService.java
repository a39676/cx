package demo.interaction.autoTest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;

public interface AutoTestDemoService {

	ModelAndView linkToATHome();

	ModelAndView index();

	/**
	 * 查找报告列表
	 * @param request 
	 * @param dto
	 * @return
	 */
	String findReportsByCondition(HttpServletRequest request, FindTestEventPageByConditionDTO dto);

	/**
	 * 查找报告
	 * @param dto
	 * @return
	 */
	ModelAndView findReportByTestEventId(HttpServletRequest request, FindReportByTestEventIdDTO dto);

	/**
	 * 新加 bing demo 
	 * @param dto
	 * @param request
	 * @return
	 */
	InsertSearchingDemoEventResult insertSearchingDemoTestEvent(InsertSearchingDemoTestEventDTO dto, HttpServletRequest request);

}
