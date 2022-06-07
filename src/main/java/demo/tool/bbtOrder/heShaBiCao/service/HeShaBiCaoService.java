package demo.tool.bbtOrder.heShaBiCao.service;

import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.dto.HeShaBiCaoWechatPreregistDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface HeShaBiCaoService {

	void sendHeShaBiCaoWechatPreregistTask(HeShaBiCaoWechatPreregistDTO dto);

	ModelAndView heShaBiCaoWechatPreregistView();

	CommonResult heShaBiCaoWechatPreregist(HeShaBiCaoWechatPreregistDTO dto);

	ModelAndView getReportSummaryPage(HeShaBiCaoWechatPreregistDTO dto);

}
