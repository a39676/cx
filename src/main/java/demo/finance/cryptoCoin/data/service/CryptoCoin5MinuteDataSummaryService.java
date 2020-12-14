package demo.finance.cryptoCoin.data.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

}
