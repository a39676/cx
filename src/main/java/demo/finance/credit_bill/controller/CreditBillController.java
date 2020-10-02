package demo.finance.credit_bill.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import demo.common.controller.CommonController;
import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.credit_bill.pojo.BillInfo;
import demo.finance.credit_bill.pojo.BillInfoCustomDetail;
import demo.finance.credit_bill.service.CreditBillsService;

@Controller
public class CreditBillController extends CommonController {

	@Autowired
	private CreditBillsService creditBillsService;
	
	public List<BillInfo> getAccountBillInfo(List<AccountInfo> accountInfoIds) {
		return creditBillsService.getBillBaseInfoByAccountInfos(accountInfoIds);
	}
	
	public List<BillInfo> getBillBaseInfoByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos) {
		return creditBillsService.getBillBaseInfoByAccountInfoWithBankInfo(accountInfos);
	}
	
	public List<BillInfoCustomDetail> getBillCustomDetailByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos) {
		return creditBillsService.getBillCustomDetailByAccountInfoWithBankInfo(accountInfos);
	}
}
