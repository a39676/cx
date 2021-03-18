package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public interface CryptoCoinPriceCacheService {
	
	void reciveData(CryptoCoinPriceCommonDataBO newBO);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	CryptoCoinPriceCommonDataBO dataStrToBO(String str);

	boolean isSocketAlive();

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime);

}
