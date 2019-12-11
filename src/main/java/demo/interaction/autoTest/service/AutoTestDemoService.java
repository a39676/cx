package demo.interaction.autoTest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;

public interface AutoTestDemoService {

	ModelAndView linkToATHome(HttpServletRequest request);

	ModelAndView index();

	/**
	 * 查找报告列表
	 * @param dto
	 * @return
	 */
	String findReportsByCondition(FindTestEventPageByConditionDTO dto);

	/**
	 * 查找报告
	 * @param dto
	 * @return
	 */
	ModelAndView findReportByTestEventId(FindReportByTestEventIdDTO dto);

	/**
	 * 新加 bing demo 
	 * @param dto
	 * @param request
	 * @return
	 */
	InsertSearchingDemoEventResult insertSearchingDemoTestEvent(InsertSearchingDemoTestEventDTO dto, HttpServletRequest request);

}
