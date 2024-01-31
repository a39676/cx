package demo.finance.cnStockMarket.service;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;

public interface CnStockMarketService {

	CommonResult receiveCnStockMarketData(CnStockMarketDataDTO dto);

}
