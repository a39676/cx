package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

}
