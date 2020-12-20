package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1week;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPriceCommonData;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1WeekDataSummaryService {

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice1week> getData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes);

	List<CryptoCoinPriceCommonData> getCommonData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes);


}
