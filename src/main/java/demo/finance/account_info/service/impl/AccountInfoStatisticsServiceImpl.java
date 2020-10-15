package demo.finance.account_info.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.bo.AccountStatisticsByBankIdBO;
import demo.finance.account_info.pojo.type.AccountType;
import demo.finance.account_info.service.AccountInfoStatisticsService;

@Service
public class AccountInfoStatisticsServiceImpl implements AccountInfoStatisticsService{

	@Override
	public List<AccountStatisticsByBankIdBO> accountStatisticsByBankId(List<AccountInfoWithBankInfo> accountInfoList) {
		
		List<AccountStatisticsByBankIdBO> resultList = new ArrayList<AccountStatisticsByBankIdBO>();
		
		AccountStatisticsByBankIdBO statistican;
		
		for(AccountInfoWithBankInfo account : accountInfoList) {
			if(AccountType.creditAccount.getCode().equals(account.getAccountType())) {
				if((statistican = getStatisticsByBankId(resultList, account.getBankId())) != null) {
					statistican.addLiability(account.getAccountBalance());
					statistican.addSharingQuota(account);
				} else {
					statistican = new AccountStatisticsByBankIdBO();
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
	public AccountStatisticsByBankIdBO getStatisticsByBankId(List<AccountStatisticsByBankIdBO> accountList, Long bankId) {
		for(AccountStatisticsByBankIdBO account : accountList) {
			if(account.getBankId().equals(bankId)) {
				return account;
			}
		}
		return null;
	}
}
