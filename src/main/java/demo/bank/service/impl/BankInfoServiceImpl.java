package demo.bank.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bank.mapper.BankInfoMapper;
import demo.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.bank.pojo.po.BankInfo;
import demo.bank.pojo.result.FindBankInfoResult;
import demo.bank.service.BankInfoService;
import net.sf.json.JSONObject;

@Service
public class BankInfoServiceImpl implements BankInfoService{
	
	@Autowired
	private BankInfoMapper bankInfoMapper;
	
	Logger logger = LoggerFactory.getLogger(BankInfoServiceImpl.class);
	
	@Override
	public List<BankInfo> searchBankListByName(String jsonStrInput) {
		JSONObject jsonInput = JSONObject.fromObject(jsonStrInput);
		String bankName = jsonInput.getString("bankName");
		
		if(StringUtils.isBlank(bankName)) {
			return new ArrayList<BankInfo>();
		}
		
		FindBankInfoParam mapperParam = new FindBankInfoParam();
		mapperParam.setFuzzyBankName(bankName);
		List<BankInfo> bankList = bankInfoMapper.findBankInfoByCondition(mapperParam);
		
		return bankList;
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
