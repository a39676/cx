package demo.bank.service;

import java.util.List;

import demo.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.bank.pojo.po.BankUnion;

public interface BankUnionService {

	public List<BankUnion> getCommonBankUnion();

	List<BankUnion> searchBankUnionByCondition(FindBankUnionParam param);

	BankUnion getById(Long id);

	List<BankUnion> getByIdList(List<Long> idList);
}
