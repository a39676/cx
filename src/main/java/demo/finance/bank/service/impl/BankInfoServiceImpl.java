package demo.finance.bank.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.bank.mapper.BankInfoMapper;
import demo.finance.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.finance.bank.pojo.dto.BankButtonListQueryDTO;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.po.BankInfo;
import demo.finance.bank.pojo.result.FindBankInfoResult;
import demo.finance.bank.pojo.result.SearchBankListByNameResult;
import demo.finance.bank.service.BankInfoService;

@Service
public class BankInfoServiceImpl implements BankInfoService{
	
	@Autowired
	private BankInfoMapper bankInfoMapper;
	
	Logger logger = LoggerFactory.getLogger(BankInfoServiceImpl.class);
	
	@Override
	public SearchBankListByNameResult searchBankListByName(BankButtonListQueryDTO dto) {
		SearchBankListByNameResult r = new SearchBankListByNameResult();
		if(StringUtils.isBlank(dto.getBankName())) {
			return r;
		}
		
		FindBankInfoParam mapperParam = new FindBankInfoParam();
		mapperParam.setFuzzyBankName(dto.getBankName());
		List<BankInfo> bankList = bankInfoMapper.findBankInfoByCondition(mapperParam);
		
		r.setBankList(bankList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public List<BankInfo> getCommonBankInfo() {
		return bankInfoMapper.get8601BankList();
	}

	@Override
	public BankInfoWithBankUnionBO getBankInfoWithBankUnionByBankId(Long bankId) {
		return bankInfoMapper.getBankFullInfoById(bankId);
	}
	
	@Override
	public FindBankInfoResult getBankInfoByCondition(FindBankInfoParam cp) {
		FindBankInfoResult result = new FindBankInfoResult();
		List<BankInfo> bankList = bankInfoMapper.findBankInfoByCondition(cp);
		result.setBankList(bankList);
		result.setIsSuccess();
		return result;
	}
}
