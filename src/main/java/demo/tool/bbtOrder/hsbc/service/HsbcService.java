package demo.tool.bbtOrder.hsbc.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.hsbc.pojo.dto.HeShaBiCaoWechatPreregistDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface HsbcService {

	void sendHsbcWechatPreregistTask(HeShaBiCaoWechatPreregistDTO dto);

	ModelAndView hsbcWechatPreregistView();

	CommonResult hsbcWechatPreregist(HeShaBiCaoWechatPreregistDTO dto);

	ModelAndView getReportSummaryPage(HeShaBiCaoWechatPreregistDTO dto);

}
