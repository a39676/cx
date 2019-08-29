package demo.trading.mapper;

import demo.trading.pojo.po.TradingRecorderMarker;

public interface TradingRecorderMarkerCustomMapper extends TradingRecorderMarkerMapper{

	TradingRecorderMarker getTradingRecordMarkerByTradingId(Integer tradingRecorderId);
	
}
