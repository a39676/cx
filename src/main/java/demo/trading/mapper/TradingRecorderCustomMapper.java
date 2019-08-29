package demo.trading.mapper;

import demo.trading.pojo.po.TradingRecorder;

public interface TradingRecorderCustomMapper extends TradingRecorderMapper{

	TradingRecorder getTradingRecordById(Long tradingId);
	
	int isnertTradingRecorder(TradingRecorder tradingRecorder);
}
