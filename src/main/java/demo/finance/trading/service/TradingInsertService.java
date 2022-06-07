package demo.finance.trading.service;

import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.result.InsertTradingRecorderResult;

public interface TradingInsertService {

	InsertTradingRecorderResult insertTradingRecorder(InsertNewTransationDTO p, Long accountId);

	TradingRecorder getTradingRecordById(Long tradingRecorderId);

}
