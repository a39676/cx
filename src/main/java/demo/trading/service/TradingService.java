package demo.trading.service;

import java.util.List;

import demo.account_info.pojo.po.AccountInfo;
import demo.baseCommon.pojo.param.controllerParam.InsertNewTransationParam;
import demo.trading.pojo.CommonTransationParties;
import demo.trading.pojo.po.TradingRecorder;
import demo.trading.pojo.result.InsertTradingRecorderResult;

public interface TradingService {

	InsertTradingRecorderResult insertTradingRecorder(InsertNewTransationParam p, Long accountId);

	long insertTradingRecorderFromFileLine(String strLineInput, AccountInfo accountInfo);

	TradingRecorder getTradingRecordById(Long tradingRecorderId);

	List<CommonTransationParties> getCurrentCommonTransation(Long holderId, Integer limit);

	String importTradingRecordFromFiles(String tradingRecordTxtPath);

}
