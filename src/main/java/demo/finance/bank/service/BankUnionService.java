package demo.finance.bank.service;

import java.util.List;

import demo.finance.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.finance.bank.pojo.po.BankUnion;

public interface BankUnionService {

	public List<BankUnion> getCommonBankUnion();

	List<BankUnion> searchBankUnionByCondition(FindBankUnionParam param);

	BankUnion getById(Long id);

	List<BankUnion> getByIdList(List<Long> idList);
}
