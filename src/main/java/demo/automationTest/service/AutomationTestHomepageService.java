package demo.automationTest.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import demo.automationTest.pojo.result.InsertSearchingDemoEventResult;
import demo.automationTest.pojo.vo.TestReportSummaryVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AutomationTestHomepageService {

	ModelAndView linkToATHome(HttpServletRequest request);

	ModelAndView index();

	/**
	 * 查找报告列表
	 * @param request 
	 * @param dto
	 * @return
	 */
	List<TestReportSummaryVO> findReportsByCondition(FindTestEventPageByConditionDTO dto);

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
