package demo.trading.mapper;

import demo.trading.pojo.po.TradingRecorderMarker;

public interface TradingRecorderMarkerMapper {
    int insert(TradingRecorderMarker record);

    int insertSelective(TradingRecorderMarker record);
    
    TradingRecorderMarker getTradingRecordMarkerByTradingId(Integer tradingRecorderId);
}