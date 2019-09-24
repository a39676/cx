package demo.account_info.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import demo.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.account_info.pojo.dto.controllerDTO.AccountInfoRegistDTO;
import demo.account_info.pojo.dto.controllerDTO.AccountNumberDuplicateCheckDTO;
import demo.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.account_info.pojo.dto.controllerDTO.GetAccountListByConditionParam;
import demo.account_info.pojo.po.AccountInfo;
import demo.account_info.pojo.result.AccountRegistResult;
import demo.account_info.pojo.result.GetAccountListResult;
import demo.account_info.pojo.result.GetAccountNumberAndAliasListResult;
import demo.account_info.pojo.result.InsertTransationResult;
import demo.account_info.pojo.vo.SummaryAccountsByBankId;
import demo.baseCommon.pojo.param.controllerParam.InsertNewTransationParam;
import demo.baseCommon.pojo.result.CommonResult;

public interface AccountInfoService {
	
	AccountRegistResult accountRegistration(AccountInfoRegistDTO dto);

	AccountInfo getAccountInfoById(int id);
	
	CommonResult accountNumberDuplicateCheck(AccountNumberDuplicateCheckDTO dto);
	
	String getMainAccountNum(int id);

	InsertTransationResult insertTradingRecorderSelective(InsertNewTransationParam param) throws Exception;

	int updateAccountMarker(String accountNumber);

	AccountInfoWithBankInfo getAccountInfoWithBankInfoByAccountNumber(String accountNumber);

	List<SummaryAccountsByBankId> sumAccountList(List<AccountInfoWithBankInfo> accountList);

	List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByHolderId(Long holderId);

	List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByCondition(Long holderId, GetAccountListByConditionParam controllerParam);

	int modifyAccountInfoVaildDate(String vaildDateString, String accountNumber, boolean isAdmin);

	List<AccountInfo> getAllAffiliatedAccountByAffiliationId(String accountNumber);

	List<AccountInfo> getAllAffiliatedAccountByAffiliationId(Long accountAffiliatioId);

	HashMap<String, String> sumAffiliatedAccountInfo(List<AccountInfo> affiliatedAccounts);

	int modifyCreditsQuota(String newCreditsQuota, String accountNumber);

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

	List<AccountInfo> findByCondition(FindAccountInfoByConditionDTO dto);

	List<AccountInfo> findCurrentAccountInfoListByCondition(FindAccountInfoByConditionDTO dto);

	List<AccountInfo> findAccountsByCondition(FindAccountInfoByConditionDTO dto);

}
