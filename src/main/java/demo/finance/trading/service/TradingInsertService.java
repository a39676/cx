package demo.finance.trading.service;

import java.util.List;

import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.trading.pojo.CommonTransationParties;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.result.InsertTradingRecorderResult;

public interface TradingInsertService {

	InsertTradingRecorderResult insertTradingRecorder(InsertNewTransationDTO p, Long accountId);

	long insertTradingRecorderFromFileLine(String strLineInput, AccountInfo accountInfo);

	TradingRecorder getTradingRecordById(Long tradingRecorderId);

	List<CommonTransationParties> getCurrentCommonTransation(Long holderId, Integer limit);

	String importTradingRecordFromFiles(String tradingRecordTxtPath);

}
