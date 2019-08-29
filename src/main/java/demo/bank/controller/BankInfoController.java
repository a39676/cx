package demo.bank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import demo.bank.pojo.bo.BankInfoCustomBO;
import demo.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.bank.pojo.constant.BankUrl;
import demo.bank.pojo.constant.BankViews;
import demo.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.bank.pojo.po.BankInfo;
import demo.bank.pojo.result.FindBankInfoResult;
import demo.bank.service.BankInfoService;
import demo.base.system.pojo.constant.BaseStatusCode;
import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = BankUrl.root)
public class BankInfoController extends CommonController {
	
	@Autowired
	private BankUnionController bankUnionController;
	
	@Autowired
	private BankInfoService bankInfoService;
	
	
	@GetMapping(value = BankUrl.normalBankInfoCustomList)
	public BankInfoCustomBO getNormalBankInfoCustom() {
		BankInfoCustomBO bankInfoCustom = new BankInfoCustomBO();
		bankInfoCustom.setBankInfoList(bankInfoService.getCommonBankInfo());
		bankInfoCustom.setBankUnionList(bankUnionController.getNormalBankUnionHashMap());
		
		return bankInfoCustom;
	}
	
	public BankInfoWithBankUnionBO getBankInfoCustomByBankId(Long bankId) {
		return bankInfoService.getBankInfoWithBankUnionByBankId(bankId);
	}
	
	@PostMapping(value = BankUrl.getBankInfoByCondition)
	@ResponseBody
	public FindBankInfoResult getBankInfoByCondition(@RequestBody FindBankInfoParam cp) {
		FindBankInfoResult result = bankInfoService.getBankInfoByCondition(cp);
		return result;
	}
	

	
	@PostMapping(value = BankUrl.bankSelectorV2)
	public ModelAndView getBankButton() {
		ModelAndView view = new ModelAndView(BankViews.bankSelectorV2);
		return view;
	}
	
	@PostMapping(value = BankUrl.bankButtonList)
	public void getBankButton(
			@RequestBody String jsonStrInput,
			HttpServletResponse response) throws IOException {
		
		List<BankInfo> bankList;
		if(jsonStrInput == null || !(jsonStrInput.length() > 0)) {
			bankList = new ArrayList<BankInfo>();
		} else {
			bankList = bankInfoService.searchBankListByName(jsonStrInput);
		}
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jsonOutput = null;
		
		resultMap.put("result", BaseStatusCode.success);
		resultMap.put("bankList", bankList);

		jsonOutput = JSONObject.fromObject(resultMap);
		
		response.getWriter().println(jsonOutput);
	}
	
	@GetMapping(value = BankUrl.bankSelectorV4)
	public ModelAndView getBankButtonV4() {
		ModelAndView view = new ModelAndView(BankViews.bankSelectorV4);
		return view;
	}
	
	@PostMapping(value = BankUrl.bankSelectorV4)
	public void getBankButtonV4(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		FindBankInfoParam param = new FindBankInfoParam().fromJson(getJson(data));
		FindBankInfoResult result = bankInfoService.getBankInfoByCondition(param);
		outputJson(response, JSONObject.fromObject(result));
	}
}
