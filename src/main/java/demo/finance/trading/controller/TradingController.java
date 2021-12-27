package demo.finance.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.trading.pojo.constant.TradingUrl;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.result.InsertTradingRecorderResult;
import demo.finance.trading.service.TradingInsertService;

@Controller
@RequestMapping(value = TradingUrl.tradingRoot)
public class TradingController {
	
	
	@Autowired
	private TradingInsertService tradingService;
	
	
	public InsertTradingRecorderResult insertTradingRecorderSelective(InsertNewTransationDTO param, Long accountId){
		return tradingService.insertTradingRecorder(param, accountId);
	}
	
	public TradingRecorder getTradingRecorderById(Long tradingRecorderId) {
		return tradingService.getTradingRecordById(tradingRecorderId);
	}
	
}