package demo.finance.trading.mapper;

import demo.finance.trading.pojo.po.TradingRecorderMarker;

public interface TradingRecorderMarkerMapper {
    int insert(TradingRecorderMarker record);

    int insertSelective(TradingRecorderMarker record);
    
    TradingRecorderMarker getTradingRecordMarkerByTradingId(Integer tradingRecorderId);
}