package demo.finance.cryptoCoin.data.service;

import finance.cryptoCoin.binance.pojo.result.CryptoCoinBinanceAccountSummaryResult;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public interface CryptoCoinAccountInfoQueryService {

	CryptoCoinBinanceAccountSummaryResult getAccountSummary(CryptoCoinInteractionCommonDTO dto);

}
