package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public interface CryptoCoinHistoryDataService {

	List<CryptoCoinPriceCommonDataBO> getHistoryDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			TimeUnitType timeUnit, Integer timeRange);

}
