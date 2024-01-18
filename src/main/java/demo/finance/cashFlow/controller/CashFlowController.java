package demo.finance.cashFlow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.finance.cashFlow.pojo.constant.CashFlowUrl;
import demo.finance.cashFlow.pojo.result.CashFlowSummaryResult;
import demo.finance.cashFlow.service.CashFlowService;

@Controller
@RequestMapping(value = CashFlowUrl.ROOT)
public class CashFlowController {

	@Autowired
	private CashFlowService cashFlowService;

	@GetMapping(value = CashFlowUrl.GET_SUMMARY)
	@ResponseBody
	public CashFlowSummaryResult getSummary() {
		return cashFlowService.getSummary();
	}
}
