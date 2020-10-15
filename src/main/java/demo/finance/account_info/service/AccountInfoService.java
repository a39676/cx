package demo.finance.account_info.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.dto.ModifyValidDateDTO;
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

public interface AccountInfoService {
	
	AccountRegistResult accountRegistration(AccountInfoRegistDTO dto);

	CommonResult accountNumberDuplicateCheck(AccountNumberDuplicateCheckDTO dto);
	
	String getMainAccountNum(int id);

	InsertTransationResult insertTradingRecorderSelective(InsertNewTransationDTO param) throws Exception;

	int updateAccountMarker(String accountNumber);

	AccountInfoWithBankInfo getAccountInfoWithBankInfoByAccountNumber(String accountNumber);

	List<SummaryAccountsByBankId> sumAccountList(List<AccountInfoWithBankInfo> accountList);

	List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByHolderId(Long holderId);

	List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByCondition(Long holderId, GetAccountListByConditionParam controllerParam);

	List<AccountInfo> getAllAffiliatedAccountByAffiliationId(String accountNumber);

	List<AccountInfo> getAllAffiliatedAccountByAffiliationId(Long accountAffiliatioId);

	HashMap<String, String> sumAffiliatedAccountInfo(List<AccountInfo> affiliatedAccounts);

	int modifyTemproraryCreditsQuota(String newTemproraryCreditsQuota, String accountNumber);

	int modifyAccountInfoTemproraryCreditsVaildDate(String temproraryCreditsVaildDate, String accountNumber,
			boolean isAdmin);

	AccountInfo getAccountInfoByAccountNumber(String accountNumber);

	boolean checkAccountNumberBelongUser(String accountNumber);

	/**
	 * 根据尾号确认卡片是否归属
	 * @param accountTailNumber
	 * @return
	 */
	boolean checkAccountBelongUserByTailNumber(String accountTailNumber);

	boolean accountMarkerVerify(AccountInfo accountInfo);

	boolean updateAccountAmount(AccountInfo targetAccount, BigDecimal transationAmount);

	List<AccountInfoWithBankInfo> accountUsedQuotaStatistics(List<AccountInfoWithBankInfo> accountInfoList);

	/** 
	 * 获取账户列表 
	 * 2019-07-04 
	 * 发现耦合度过高, 需要拆改或重写.
	 * */
	GetAccountListResult accountInfoWithBankInfoList(GetAccountListByConditionParam param);

	List<String> findAccountNumberListByCondition(FindAccountInfoByConditionDTO dto);

	GetAccountNumberAndAliasListResult findCurrentAccountNumberListByCondition(FindAccountInfoByConditionDTO dto);

	List<AccountInfo> findCurrentAccountInfoListByCondition(FindAccountInfoByConditionDTO dto);

	List<AccountInfo> findAccountsByCondition(FindAccountInfoByConditionDTO dto);

	CommonResultCX modifyCreditsQuota(ModifyCreditsQuotaDTO dto);

	CommonResult modifyAccountInfoVaildDate(ModifyValidDateDTO dto) throws Exception;

}
