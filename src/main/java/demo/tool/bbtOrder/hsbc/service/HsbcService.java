package demo.tool.bbtOrder.hsbc.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.hsbc.pojo.dto.HsbcWechatPreregistDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface HsbcService {

	void sendHsbcWechatPreregistTask(HsbcWechatPreregistDTO dto);

	ModelAndView hsbcWechatPreregistView();

	CommonResult hsbcWechatPreregist(HsbcWechatPreregistDTO dto);

	ModelAndView getReportSummaryPage(HsbcWechatPreregistDTO dto);

	CommonResult hsbcWechatPreregistRandomInsert();

}
