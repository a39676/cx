package demo.tool.bbtOrder.underWayMonthTest.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayTrainProjectDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface UnderWayService {

	CommonResult monthTest(UnderWayMonthTestDTO dto);

	ModelAndView monthTestView();

	CommonResult trainProject(UnderWayTrainProjectDTO inputDTO);

	ModelAndView trainProject();

}
