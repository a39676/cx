package demo.finance.account_info.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.statistics.AccountStatisticsByBankId;
import demo.finance.account_info.pojo.type.AccountType;
import demo.finance.account_info.service.AccountInfoStatisticsService;

@Service
public class AccountInfoStatisticsServiceImpl implements AccountInfoStatisticsService{

	@Override
	public List<AccountStatisticsByBankId> accountStatisticsByBankId(List<AccountInfoWithBankInfo> accountInfoList) {
		
		List<AccountStatisticsByBankId> resultList = new ArrayList<AccountStatisticsByBankId>();
		
		AccountStatisticsByBankId statistican;
		
		for(AccountInfoWithBankInfo account : accountInfoList) {
			if(AccountType.creditAccount.getCode().equals(account.getAccountType())) {
				if((statistican = getStatisticsByBankId(resultList, account.getBankId())) != null) {
					statistican.addLiability(account.getAccountBalance());
					statistican.addSharingQuota(account);
				} else {
					statistican = new AccountStatisticsByBankId();
					statistican.setBankId(account.getBankId());
					statistican.addLiability(account.getAccountBalance());
					statistican.addSharingQuota(account);
					resultList.add(statistican);
				}
			}
		}
		
		return resultList;
	}
	
	@Override
	public AccountStatisticsByBankId getStatisticsByBankId(List<AccountStatisticsByBankId> accountList, Long bankId) {
		for(AccountStatisticsByBankId account : accountList) {
			if(account.getBankId().equals(bankId)) {
				return account;
			}
		}
		return null;
	}
}
