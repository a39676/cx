package demo.bank.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import demo.bank.pojo.constant.BankUnionUrl;
import demo.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.bank.pojo.po.BankUnion;
import demo.bank.pojo.result.FindBankUnionResult;
import demo.bank.service.BankUnionService;
import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = BankUnionUrl.root)
public class BankUnionController extends CommonController {
	
	@Autowired
	private BankUnionService bankUnionService;
	
	public List<BankUnion> getNormalBankUnionHashMap() {
		return bankUnionService.getCommonBankUnion();
	}
	
	@PostMapping(value = BankUnionUrl.bankUnionSelectorV4)
	public void searchBankUnionByCondition(@RequestBody String data, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		FindBankUnionParam param = new FindBankUnionParam().fromJson(JSONObject.fromObject(data));
		List<BankUnion> bankUnionList = bankUnionService.searchBankUnionByCondition(param);
		FindBankUnionResult result = new FindBankUnionResult();
		result.setBankUnionList(bankUnionList);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = BankUnionUrl.defaultBankUnion)
	public List<BankUnion> defaultBankUnion() {
		return bankUnionService.searchBankUnionByCondition(new FindBankUnionParam());
	}
}
