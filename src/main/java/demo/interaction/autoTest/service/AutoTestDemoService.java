package demo.interaction.autoTest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;

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
	 * 新加 自动化测试任务
	 * @param dto
	 * @param request
	 * @return
	 */
	InsertSearchingDemoEventResult insertSearchingDemoTestEvent(BingSearchInHomePageDTO dto,
			HttpServletRequest request);

}
