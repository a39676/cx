package demo.finance.bank.service;

import java.util.List;

import demo.finance.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.finance.bank.pojo.dto.BankButtonListQueryDTO;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.po.BankInfo;
import demo.finance.bank.pojo.result.FindBankInfoResult;
import demo.finance.bank.pojo.result.SearchBankListByNameResult;

public interface BankInfoService {

	public SearchBankListByNameResult searchBankListByName(BankButtonListQueryDTO dto);
	
	public List<BankInfo> getCommonBankInfo();

	public BankInfoWithBankUnionBO getBankInfoWithBankUnionByBankId(Long bankId);

	FindBankInfoResult getBankInfoByCondition(FindBankInfoParam cp);
}
