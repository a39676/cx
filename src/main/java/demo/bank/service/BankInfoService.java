package demo.bank.service;

import java.util.List;

import demo.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.bank.pojo.po.BankInfo;
import demo.bank.pojo.result.FindBankInfoResult;

public interface BankInfoService {

	public List<BankInfo> searchBankListByName(String bankName);
	
	public List<BankInfo> getCommonBankInfo();

	public BankInfoWithBankUnionBO getBankInfoWithBankUnionByBankId(Long bankId);

	FindBankInfoResult getBankInfoByCondition(FindBankInfoParam cp);
}
