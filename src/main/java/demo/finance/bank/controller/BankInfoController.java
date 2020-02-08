package demo.finance.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.finance.bank.pojo.bo.BankInfoCustomBO;
import demo.finance.bank.pojo.bo.BankInfoWithBankUnionBO;
import demo.finance.bank.pojo.constant.BankUrl;
import demo.finance.bank.pojo.constant.BankViews;
import demo.finance.bank.pojo.dto.BankButtonListQueryDTO;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.result.FindBankInfoResult;
import demo.finance.bank.pojo.result.SearchBankListByNameResult;
import demo.finance.bank.service.BankInfoService;

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
	@ResponseBody
	public SearchBankListByNameResult getBankButton(@RequestBody BankButtonListQueryDTO dto) {
		return bankInfoService.searchBankListByName(dto);
	}
	
	@GetMapping(value = BankUrl.bankSelectorV4)
	public ModelAndView getBankButtonV4() {
		ModelAndView view = new ModelAndView(BankViews.bankSelectorV4);
		return view;
	}
	
	@PostMapping(value = BankUrl.bankSelectorV4)
	@ResponseBody
	public FindBankInfoResult getBankButtonV4(@RequestBody FindBankInfoParam param) {
		return bankInfoService.getBankInfoByCondition(param);
	}
}
