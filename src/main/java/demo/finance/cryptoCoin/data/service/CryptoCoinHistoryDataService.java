package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public interface CryptoCoinHistoryDataService {

	List<CryptoCoinPriceCommonDataBO> getHistoryDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			TimeUnitType timeUnit, Integer timeRange);

}
