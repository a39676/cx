package demo.trading.service;

import demo.trading.pojo.dto.TradingRecorderDTO;
import demo.trading.pojo.result.TradingQueryResult;

public interface TradingQueryService {

	TradingQueryResult findTradingRecordByCondition(TradingRecorderDTO dto);

}
