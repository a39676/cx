package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public interface CryptoCoinPriceCacheService {
	
	void reciveData(CryptoCoinPriceCommonDataBO newBO);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime);

	boolean isSocketAlive();

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime datetime);

	CryptoCoinPriceCommonDataBO getNewPrice(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType);

	void cleanOldHistoryData();

}
