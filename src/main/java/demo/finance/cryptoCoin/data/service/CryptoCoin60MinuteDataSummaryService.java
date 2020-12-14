package demo.finance.cryptoCoin.data.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface CryptoCoin60MinuteDataSummaryService {

	CommonResult summaryHistoryData();

	CommonResult deleteExpiredCacheData();

}
