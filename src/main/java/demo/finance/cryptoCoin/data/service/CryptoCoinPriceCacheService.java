package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoinPriceCacheService {
	
	void reciveData(CryptoCoinPriceCommonDataBO newBO);

	List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType);

}
