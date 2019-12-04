package demo.finance.bank.mapper;

import java.util.List;

import demo.finance.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.po.BankInfo;

public interface BankInfoMapper {
    int insert(BankInfo record);

	int insertSelective(BankInfo record);
	
	List<BankInfo> get8601BankList();
    
	BankInfoWithBankUnionBO getBankFullInfoById(Long bankId);
	
	List<BankInfo> getBankInfo(BankInfo bankInfoQueryVO);
	
	List<BankInfo> loadBankList();
	
	List<BankInfo> findBankInfoByCondition(FindBankInfoParam param);
	

}