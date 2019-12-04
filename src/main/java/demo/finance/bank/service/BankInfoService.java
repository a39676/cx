package demo.finance.bank.service;

import java.util.List;

import demo.finance.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.po.BankInfo;
import demo.finance.bank.pojo.result.FindBankInfoResult;

public interface BankInfoService {

	public List<BankInfo> searchBankListByName(String bankName);
	
	public List<BankInfo> getCommonBankInfo();

	public BankInfoWithBankUnionBO getBankInfoWithBankUnionByBankId(Long bankId);

	FindBankInfoResult getBankInfoByCondition(FindBankInfoParam cp);
}
