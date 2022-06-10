package demo.tool.bbtOrder.underWayMonthTest.service;

import autoTest.testEvent.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface UnderWayMonthTestService {

	void sendMonthTestTask(UnderWayMonthTestDTO paramDTO);

	CommonResult monthTest(UnderWayMonthTestDTO dto);

}
