package demo.finance.account_info.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.constant.BaseViewConstant;
import demo.common.controller.CommonController;
import demo.common.pojo.result.CommonResultCX;
import demo.config.costom_component.BaseUtilCustom;
import demo.finance.account_holder.controller.AccountHolderController;
import demo.finance.account_holder.pojo.constant.AccountHolderViewConstants;
import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.constant.AccountInfoView;
import demo.finance.account_info.pojo.constant.AccountUrl;
import demo.finance.account_info.pojo.dto.ModifyValidDateDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.AccountInfoDetailQueryDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.AccountInfoRegistDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.AccountNumberDuplicateCheckDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.GetAccountListByConditionParam;
import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.ModifyCreditsQuotaDTO;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.account_info.pojo.result.AccountRegistResult;
import demo.finance.account_info.pojo.result.GetAccountListResult;
import demo.finance.account_info.pojo.result.GetAccountNumberAndAliasListResult;
import demo.finance.account_info.pojo.result.InsertTransationResult;
import demo.finance.account_info.pojo.vo.SummaryAccountsByBankId;
import demo.finance.account_info.service.AccountInfoService;
import demo.finance.bank.controller.BankInfoController;
import demo.finance.bank.pojo.bo.BankInfoCustomBO;
import demo.finance.credit_bill.controller.CreditBillController;
import demo.finance.credit_bill.pojo.BillInfoCustomDetail;
import demo.finance.trading.pojo.constant.TradingViews;

@Controller
@RequestMapping(value = AccountUrl.accountInfoRoot)
public class AccountInfoController extends CommonController {
	
	@Autowired
	private BankInfoController bankInfoController;
	
	@Autowired
	private AccountHolderController accountHolderController;
	
	@Autowired
	private CreditBillController creditbillController;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@Autowired
	private AccountInfoService accountInfoService;
	
	@PostMapping(value = AccountUrl.accountDetail)
	public ModelAndView accountDetail(@RequestBody AccountInfoDetailQueryDTO dto) {
		ModelAndView view = new ModelAndView();

		try{
			view.setViewName(AccountInfoView.ACCOUNT_DETAIL);
			
			AccountInfoWithBankInfo accountInfoWithBankInfo = accountInfoService.getAccountInfoWithBankInfoByAccountNumber(dto.getAccountNumber());
			view.addObject("account", accountInfoWithBankInfo);
			
			List<AccountInfo> affiliationAccounts = accountInfoService.getAllAffiliatedAccountByAffiliationId(accountInfoWithBankInfo.getAccountId());
			if(affiliationAccounts != null 
					&& !affiliationAccounts.isEmpty()) {
				
				view.addObject("affiliationList", affiliationAccounts);
				
				HashMap<String, String> affiliationSum =accountInfoService.sumAffiliatedAccountInfo(affiliationAccounts);
				
				view.addObject("affiliationSum", affiliationSum);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName(BaseViewConstant.viewError);
			view.addObject("exception", "wrong input");
		}
			
		return view;
	}
	
	@GetMapping(AccountUrl.accountListView)
	public ModelAndView accountList() {
		ModelAndView view = new ModelAndView(AccountInfoView.ACCOUNT_LIST);

		return view;
	}
	
	@PostMapping(AccountUrl.accountListView)
	public ModelAndView accountListView(@RequestBody GetAccountListByConditionParam param) {
		ModelAndView view = new ModelAndView(AccountInfoView.ACCOUNT_LIST);
		
		GetAccountListResult result = accountInfoService.accountInfoWithBankInfoList(param);
		
		List<AccountInfoWithBankInfo> accountList = result.getAccountList();
		
		List<BillInfoCustomDetail> billInfoList = creditbillController.getBillCustomDetailByAccountInfoWithBankInfo(accountList);
		
		view.addObject("accountList", accountList);
		view.addObject("billInfoList", billInfoList);
		
		return view;
	}
	
	@PostMapping(AccountUrl.accountList)
	@ResponseBody
	public GetAccountListResult accountList(@RequestBody GetAccountListByConditionParam param, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		GetAccountListResult result = accountInfoService.accountInfoWithBankInfoList(param);
		return result;
	}
	
	@GetMapping(value = AccountUrl.accountStatistics)
	public ModelAndView accountStatistics(RedirectAttributes redirectAttr) {
		/*
		 * 2019-07-04 准备将逻辑转移至service
		 */
		ModelAndView view = new ModelAndView(AccountInfoView.ACCOUNT_STATISTICS);
		
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();

		if(holderList == null || holderList.isEmpty() || holderList.get(0) == null) {
			view.setViewName(AccountHolderViewConstants.holderRegistration);
			view.addObject("message", "请先建立一个持卡人");
			return view;
		} 

		List<AccountInfoWithBankInfo> accountList = getCurrentAccountInfoWithBankInfo();
		if(accountList == null || accountList.isEmpty()) {
			view.setViewName("redirect:" + AccountUrl.accountInfo + AccountUrl.accountRegist);
			redirectAttr.addFlashAttribute("message", "请先至少建立一个账户");
		}

		accountList = accountInfoService.accountUsedQuotaStatistics(accountList);
		
		List<SummaryAccountsByBankId> accountSumByBankId = accountInfoService.sumAccountList(accountList);

		List<BillInfoCustomDetail> billInfoList = creditbillController.getBillCustomDetailByAccountInfoWithBankInfo(accountList);
		
		BankInfoCustomBO bankInfoCustom = bankInfoController.getNormalBankInfoCustom();
		
		view.addObject("accountList", accountList);
		view.addObject("billInfoList", billInfoList);
		view.addObject("accountSumByBankId", accountSumByBankId);
		view.addObject("bankInfoCustom", bankInfoCustom);
		
		return view;
	}
	
	@PostMapping(value = AccountUrl.accountNumberDuplicateCheck)
	@ResponseBody
	public CommonResult accountNumberDuplicateCheck(
			@RequestBody AccountNumberDuplicateCheckDTO dto
			) throws IOException {
		return accountInfoService.accountNumberDuplicateCheck(dto);
	}
	
	@PostMapping(value = AccountUrl.currentAccountNumberList)
	@ResponseBody
	public GetAccountNumberAndAliasListResult accountNumberList(@RequestBody FindAccountInfoByConditionDTO dto) {
		
		GetAccountNumberAndAliasListResult result = accountInfoService.findCurrentAccountNumberListByCondition(dto);
		
		return result;
	}
	
	@GetMapping(value= AccountUrl.accountRegist, produces = "text/html;charset=UTF-8")
	public ModelAndView accountRegistration() {
		
		ModelAndView view = new ModelAndView();
		
		if (baseUtilCustom.isLoginUser()) {
			view.setViewName(AccountInfoView.ACCOUNT_REGISTRATION);
		} else { 
			view.setViewName(BaseViewConstant.view403);
			return view;
		}
		
		Long holderId = accountHolderController.getCurrentHolders().get(0).getId();
		
		if(holderId == null) {
			view.setViewName(AccountHolderViewConstants.holderRegistration);
			return view;
		}
		AccountInfo accountInfo = new AccountInfo();
		
		BankInfoCustomBO bankInfoCustom = bankInfoController.getNormalBankInfoCustom();
		
		view.addObject("title", "注册新帐号");
		view.addObject("accountInfoCustom", accountInfo);
		view.addObject("bankInfoCustom", bankInfoCustom);
		return view;
	}
	
	@PostMapping(value= AccountUrl.accountRegist)
	@ResponseBody
	public AccountRegistResult accountRegistrationResult(@RequestBody AccountInfoRegistDTO dto){
		
		AccountRegistResult result = accountInfoService.accountRegistration(dto);
		
		return result;
	}
	
	@GetMapping(value = AccountUrl.insertNewTransationV4)
	public ModelAndView insertNewTransationV4() {
		ModelAndView view = new ModelAndView();
		
		view.setViewName(TradingViews.insertNewTransationRecordV4);
		view.addObject("title", "输入交易记录");
		
		return view;
	}
	
	@PostMapping(value = AccountUrl.insertNewTransationV4)
	@ResponseBody
	public InsertTransationResult insertNewTransationV4(@RequestBody InsertNewTransationDTO p) throws Exception {
		return accountInfoService.insertTradingRecorderSelective(p);
	}

	public int updateAccountMarker(String accountNumber) {
		return accountInfoService.updateAccountMarker(accountNumber);
	}
	
	@PostMapping(value = AccountUrl.modifyVaildDate)
	@ResponseBody
	public CommonResult modifyVaildDate(@RequestBody ModifyValidDateDTO dto) throws Exception {
		return accountInfoService.modifyAccountInfoVaildDate(dto);
	}
	
	@PostMapping(value = AccountUrl.modifyCreditsQuota)
	@ResponseBody
	public CommonResultCX modifyCreditsQuota(@RequestBody ModifyCreditsQuotaDTO dto, HttpServletResponse response) throws IOException {
		return accountInfoService.modifyCreditsQuota(dto);
	}
	
	private List<AccountInfoWithBankInfo> getCurrentAccountInfoWithBankInfo() {
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();

		if(holderList == null || holderList.isEmpty() || holderList.get(0) == null) {
			return null;
		}
		
		return accountInfoService.getAccountInfoWithBankInfoByHolderId(0L + holderList.get(0).getId());
	}
	
	public AccountInfo getAccountInfoByAccountNumber(String accountNumber) {
		return accountInfoService.getAccountInfoByAccountNumber(accountNumber);
	}
	
	public boolean checkAccountNumberBelongUser(String accountNumber) {
		return accountInfoService.checkAccountNumberBelongUser(accountNumber);
	}
	
	public boolean accountMarkerVerify(AccountInfo accountInfo) {
		return accountInfoService.accountMarkerVerify(accountInfo);
	}
	
	public boolean updateAccountAmount(AccountInfo targetAccount, BigDecimal transationAmount) {
		return accountInfoService.updateAccountAmount(targetAccount, transationAmount);
	}
	
	public boolean checkAccountBelongUserByTailNumber(String accountTailNumber) {
		return accountInfoService.checkAccountBelongUserByTailNumber(accountTailNumber);
	}

	@GetMapping(AccountUrl.accountSelectorV1)
	public ModelAndView accountSelectorV1() {
		ModelAndView view = new ModelAndView(AccountInfoView.ACCOUNT_SELECTOR_V1);
		return view;
	}
	
	public List<AccountInfo> findAccountsByCondition(FindAccountInfoByConditionDTO dto) {
		return accountInfoService.findAccountsByCondition(dto);
	}
	
}
