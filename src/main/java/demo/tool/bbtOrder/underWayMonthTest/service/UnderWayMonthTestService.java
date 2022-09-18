package demo.tool.bbtOrder.underWayMonthTest.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface UnderWayMonthTestService {

	void sendMonthTestTask(UnderWayMonthTestDTO paramDTO);

	CommonResult monthTest(UnderWayMonthTestDTO dto);

	ModelAndView monthTestView();

}
