package demo.tool.bbtOrder.hsbc.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface HsbcService {

	void sendHsbcWechatPreregistTask(HsbcWechatPreregistDTO dto);

	ModelAndView hsbcWechatPreregistView();

	CommonResult hsbcWechatPreregist(HsbcWechatPreregistDTO dto);

}
