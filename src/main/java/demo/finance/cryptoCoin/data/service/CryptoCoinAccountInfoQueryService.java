package demo.finance.cryptoCoin.data.service;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import finance.cryptoCoin.common.pojo.result.CryptoCoinAccountSummaryResult;

public interface CryptoCoinAccountInfoQueryService {

	CryptoCoinAccountSummaryResult getAccountSummary(CryptoCoinInteractionCommonDTO dto);

}
