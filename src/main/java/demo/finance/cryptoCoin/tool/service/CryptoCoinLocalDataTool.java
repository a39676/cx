package demo.finance.cryptoCoin.tool.service;

import java.time.LocalDateTime;

import demo.finance.cryptoCoin.tool.pojo.result.CryptoCoinLocalDataFinderResult;

public interface CryptoCoinLocalDataTool {

	/**
	 * 查找先升后回调
	 * @return
	 */
	CryptoCoinLocalDataFinderResult finder1(LocalDateTime startDatetime, LocalDateTime endDatetime, Double upApmlitude,
			Double downApmlitude);

}
