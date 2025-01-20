package demo.finance.cryptoCoin.data.service;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.result.CryptoCoinAccountSummaryResult;

public interface CryptoCoinAccountInfoQueryService {

	CryptoCoinAccountSummaryResult getAccountSummary(CryptoCoinInteractionSingleUserCommonDTO dto);

}
