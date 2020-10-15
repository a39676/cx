package demo.finance.account_info.service;

import java.util.List;

import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.bo.AccountStatisticsByBankIdBO;

public interface AccountInfoStatisticsService {

	List<AccountStatisticsByBankIdBO> accountStatisticsByBankId(List<AccountInfoWithBankInfo> accountInfoList);

	AccountStatisticsByBankIdBO getStatisticsByBankId(List<AccountStatisticsByBankIdBO> accountList, Long bankId);

}
