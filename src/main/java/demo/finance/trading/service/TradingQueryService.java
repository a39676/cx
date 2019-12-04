package demo.finance.trading.service;

import demo.finance.trading.pojo.dto.TradingRecorderDTO;
import demo.finance.trading.pojo.result.TradingQueryResult;

public interface TradingQueryService {

	TradingQueryResult findTradingRecordByCondition(TradingRecorderDTO dto);

}
