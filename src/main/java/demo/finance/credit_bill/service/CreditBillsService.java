package demo.finance.credit_bill.service;

import java.util.List;

import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.credit_bill.pojo.BillInfoCustomDetail;
import demo.finance.credit_bill.pojo.po.BillInfo;

public interface CreditBillsService {

	List<BillInfo> getBillBaseInfoByAccountInfos(List<AccountInfo> accountInfos);

	List<BillInfo> getBillBaseInfoByAccountInfoIds(List<Long> accountIds);

	List<BillInfo> getBillBaseInfoByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos);

	List<BillInfoCustomDetail> getBillCustomDetailByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos);
	
}
