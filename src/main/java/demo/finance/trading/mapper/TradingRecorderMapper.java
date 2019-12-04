package demo.finance.trading.mapper;

import java.util.List;

import demo.finance.trading.pojo.dto.SelectTradingRecordBO;
import demo.finance.trading.pojo.po.TradingRecorder;

public interface TradingRecorderMapper {

	int insert(TradingRecorder record);

	int insertSelective(TradingRecorder record);

	TradingRecorder getTradingRecordById(Long tradingId);

	int isnertTradingRecorder(TradingRecorder tradingRecorder);

	List<TradingRecorder> selectTradingRecord(SelectTradingRecordBO bo);

}