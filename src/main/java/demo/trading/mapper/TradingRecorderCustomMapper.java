package demo.trading.mapper;

import java.util.List;

import demo.trading.pojo.dto.SelectTradingRecordBO;
import demo.trading.pojo.po.TradingRecorder;

public interface TradingRecorderCustomMapper extends TradingRecorderMapper{

	TradingRecorder getTradingRecordById(Long tradingId);
	
	int isnertTradingRecorder(TradingRecorder tradingRecorder);
	
	List<TradingRecorder> selectTradingRecord(SelectTradingRecordBO bo);
}
