package demo.finance.bank.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import demo.common.controller.CommonController;
import demo.finance.bank.pojo.constant.BankUnionUrl;
import demo.finance.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.finance.bank.pojo.po.BankUnion;
import demo.finance.bank.pojo.result.FindBankUnionResult;
import demo.finance.bank.service.BankUnionService;

@Controller
@RequestMapping(value = BankUnionUrl.root)
public class BankUnionController extends CommonController {
	
	@Autowired
	private BankUnionService bankUnionService;
	
	public List<BankUnion> getNormalBankUnionHashMap() {
		return bankUnionService.getCommonBankUnion();
	}
	
	@PostMapping(value = BankUnionUrl.bankUnionSelectorV4)
	@ResponseBody
	public FindBankUnionResult searchBankUnionByCondition(@RequestBody FindBankUnionParam param) throws JsonParseException, JsonMappingException, IOException {
		List<BankUnion> bankUnionList = bankUnionService.searchBankUnionByCondition(param);
		FindBankUnionResult result = new FindBankUnionResult();
		result.setBankUnionList(bankUnionList);
		return result;
	}
	
	@PostMapping(value = BankUnionUrl.defaultBankUnion)
	public List<BankUnion> defaultBankUnion() {
		return bankUnionService.searchBankUnionByCondition(new FindBankUnionParam());
	}
}
