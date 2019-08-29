package demo.account_info.service;

import java.util.List;

import demo.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.account_info.pojo.statistics.AccountStatisticsByBankId;

public interface AccountInfoStatisticsService {

	List<AccountStatisticsByBankId> accountStatisticsByBankId(List<AccountInfoWithBankInfo> accountInfoList);

	AccountStatisticsByBankId getStatisticsByBankId(List<AccountStatisticsByBankId> accountList, Long bankId);

}
