package demo.trading.mapper;

import demo.trading.pojo.po.TradingRecorder;

public interface TradingRecorderMapper {

	int insert(TradingRecorder record);

	int insertSelective(TradingRecorder record);
   
}