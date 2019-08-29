package demo.credit_bill.service;

import java.util.List;

import demo.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.account_info.pojo.po.AccountInfo;
import demo.credit_bill.pojo.BillInfo;
import demo.credit_bill.pojo.BillInfoCustomDetail;

public interface CreditBillsService {

	List<BillInfo> getBillBaseInfoByAccountInfos(List<AccountInfo> accountInfos);

	List<BillInfo> getBillBaseInfoByAccountInfoIds(List<Long> accountIds);

	List<BillInfo> getBillBaseInfoByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos);
	
	List<BillInfoCustomDetail> getBillCustomDetailByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos);


}
