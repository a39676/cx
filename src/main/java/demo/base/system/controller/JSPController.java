package demo.base.system.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.system.pojo.constant.JSPUrl;
import demo.common.controller.CommonController;
import demo.finance.account_holder.pojo.constant.HolderUrlConstant;
import demo.finance.account_info.pojo.constant.AccountUrl;
import demo.finance.trading.pojo.constant.TradingUrl;

@Controller
@RequestMapping(value = JSPUrl.jspRoot)
public class JSPController extends CommonController {
	
	@GetMapping(value = JSPUrl.jspFinanceclearNavigationRoleUser)
	public ModelAndView jspFinanceclearNavigationRoleUser() {
		ModelAndView view = new ModelAndView(BaseViewConstant.financeclearNavigationRoleUser);
		
		HashMap<String, String> basePart = new HashMap<String, String>();
		basePart.put("welcome", BaseUrl.welcome);
		
		HashMap<String, String> accountInfoPart = new HashMap<String, String>();
		accountInfoPart.put("accountInfoRoot", AccountUrl.accountInfoRoot);
		accountInfoPart.put("transationHistory", TradingUrl.transationHistory);
		accountInfoPart.put("accountInfo", AccountUrl.accountInfo);
		accountInfoPart.put("accountRegist", AccountUrl.accountRegist);
		accountInfoPart.put("accountListView", AccountUrl.accountListView);
		accountInfoPart.put("accountDetail", AccountUrl.accountDetail);
		accountInfoPart.put("accountStatistics", AccountUrl.accountStatistics);
		
		HashMap<String, String> accountHolderPart = new HashMap<String, String>();
		accountHolderPart.put("accountList", AccountUrl.accountList);
		accountHolderPart.put("holderRegister", HolderUrlConstant.HOLDER_REGISTER);
		
		HashMap<String, Object> userPart = new HashMap<String, Object>();
		HashMap<String, String> userOption = new HashMap<String, String>();
		userOption.put("changePassword", "");
		userPart.put("userOption", userOption);
		
		
		return view;
	}
	
	@GetMapping(value = JSPUrl.jspNavigationNotLogin)
	public ModelAndView navigationNotLogin() {
		return new ModelAndView(BaseViewConstant.navigationNotLogin);
	}
	
	@GetMapping(value = JSPUrl.jspNavigationRoleUser)
	public ModelAndView navigationRoleUser() {
		return new ModelAndView(BaseViewConstant.navigationRoleUser);
	}
	
	@GetMapping(value = JSPUrl.jspFinanceclearNavigationNotLogin)
	public ModelAndView financeclearNavigationNotLogin() {
		return new ModelAndView(BaseViewConstant.financeclearNavigationNotLogin);
	}
	
	@GetMapping(value = JSPUrl.jspNavigationAdmin)
	public ModelAndView navigationAdmin() {
		return new ModelAndView(BaseViewConstant.navigationAdmin);
	}
}
