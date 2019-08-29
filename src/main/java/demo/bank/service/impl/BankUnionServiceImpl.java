package demo.bank.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.bank.mapper.BankUnionMapper;
import demo.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.bank.pojo.po.BankUnion;
import demo.bank.pojo.po.example.BankUnionExample;
import demo.bank.pojo.po.example.BankUnionExample.Criteria;
import demo.bank.service.BankUnionService;

@Service
public class BankUnionServiceImpl implements BankUnionService {

	@Autowired
	BankUnionMapper bankUnionMapper;
	
	
	@Override
	public List<BankUnion> searchBankUnionByCondition(FindBankUnionParam param) {
		List<BankUnion> list = bankUnionMapper.findBankUnionByCondition(param);
		return list;
	}

	@Override
	public List<BankUnion> getCommonBankUnion() {
		FindBankUnionParam p = new FindBankUnionParam();
		return bankUnionMapper.findBankUnionByCondition(p);
	}

	@Override
	public BankUnion getById(Long id){
		if(id == null) {
			return new BankUnion();
		}
		
		BankUnion po = bankUnionMapper.selectByPrimaryKey(id.intValue());
		
		if(po == null) {
			po = new BankUnion();
		}
		return po;
	}
	
	@Override
	public List<BankUnion> getByIdList(List<Long> idList) {
		if(idList == null || idList.size() < 1) {
			return new ArrayList<BankUnion>();
		}
		
		BankUnionExample exp = new BankUnionExample();
		Criteria c = exp.createCriteria();
		c.andBankUnionIdIn(idList);
		
		List<BankUnion> poList = bankUnionMapper.selectByExample(exp);
		if(poList == null || poList.size() < 1) {
			poList = new ArrayList<BankUnion>();
		}
		
		return poList;
	}
}
