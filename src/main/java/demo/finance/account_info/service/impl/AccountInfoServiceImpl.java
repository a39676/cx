package demo.finance.account_info.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.TransationType;
import demo.common.service.CommonService;
import demo.finance.account_holder.controller.AccountHolderController;
import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.account_info.mapper.AccountInfoMapper;
import demo.finance.account_info.mapper.AccountInfoMarkerMapper;
import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.bo.AccountNumberWithAliasBO;
import demo.finance.account_info.pojo.bo.AccountStatisticsByBankIdBO;
import demo.finance.account_info.pojo.constant.AccountInfoConstant;
import demo.finance.account_info.pojo.dto.ModifyValidDateDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.AccountInfoRegistDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.AccountNumberDuplicateCheckDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.GetAccountListByConditionParam;
import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.account_info.pojo.dto.controllerDTO.ModifyCreditsQuotaDTO;
import demo.finance.account_info.pojo.dto.mapperDTO.GetAccountInfoWithConditionParam;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.account_info.pojo.po.AccountInfoMarker;
import demo.finance.account_info.pojo.result.AccountRegistResult;
import demo.finance.account_info.pojo.result.GetAccountListResult;
import demo.finance.account_info.pojo.result.GetAccountNumberAndAliasListResult;
import demo.finance.account_info.pojo.result.InsertTransationResult;
import demo.finance.account_info.pojo.type.AccountType;
import demo.finance.account_info.pojo.vo.SummaryAccountsByBankId;
import demo.finance.account_info.service.AccountInfoService;
import demo.finance.account_info.service.AccountInfoStatisticsService;
import demo.finance.trading.controller.TradingController;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.result.InsertTradingRecorderResult;
import toolPack.numericHandel.NumericUtilCustom;

@Service
public class AccountInfoServiceImpl extends CommonService implements AccountInfoService {
	
	@Autowired
	private NumericUtilCustom numberUtil;
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	@Autowired
	private AccountInfoMarkerMapper accountInfoMarkerMapper;

	@Autowired
	private TradingController tradingController;
	@Autowired
	private AccountHolderController accountHolderController;
	
	@Autowired
	private AccountInfoStatisticsService accountInfoStatisticsService;
	
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public AccountRegistResult accountRegistration(AccountInfoRegistDTO dto) {
		AccountRegistResult result = new AccountRegistResult();
		
		CommonResult accountNumberCheck = accountNumberDuplicateCheck(dto.getAccountNumber());
		if(!accountNumberCheck.isSuccess()) {
			result.setMessage(accountNumberCheck.getMessage());
			return result;
		}
		
		AccountHolder holder = accountHolderController.getCurrentHolders().get(0);
		/*
		 * TODO FIXME
		 * if (holder == null) {
		 *     return holderRegist??
		 * }
		 */
		Long holderId = holder.getAccountHolderId();
		
		AccountInfo newAccount = new AccountInfo();
		BeanUtils.copyProperties(dto, newAccount);
		
		StringBuffer tmpBuffer = new StringBuffer();
		boolean statusFlag = true;
		
		newAccount.setAccountHolderId(holderId);
		
		try {
			if (holderId == null || holderId < 0){
				tmpBuffer.append("Holder could not be empty \n");
				statusFlag = false;
			}
			
			if (!accountNumberCheck(dto.getAccountNumber())){
				tmpBuffer.append("Account number wrong input \n");
				statusFlag = false;
			}
			
			if (newAccount.getAccountType() == null){
				tmpBuffer.append("Account type could not be empty \n");
				statusFlag = false;
			}
			
			if(newAccount.getVaildDate() == null){
				if(AccountType.creditAccount.getCode().equals(newAccount.getAccountType())) {
					tmpBuffer.append("Vaild Date could not be empty \n");
					statusFlag = false;
				} else {
					newAccount.setVaildDate(dateHandler.dateDiffMonths(12));
				}
			}

			if (newAccount.getBankId() == null ) {
				tmpBuffer.append("Bank Id could not be empty \n");
				statusFlag = false;
			}

			if (newAccount.getBankUnionId() == null ) {
				tmpBuffer.append("Bank unionId could not be empty \n");
				statusFlag = false;
			}

			if (statusFlag){
				Long newAccountId = snowFlake.getNextId();
				newAccount.setAccountId(newAccountId);
				accountInfoMapper.accountRegistration(newAccount);
				
				AccountInfoMarker marker = new AccountInfoMarker();
				marker.setAccountId(newAccountId);
				marker.setMarker(createAccountInfoMarker(newAccount));
				accountInfoMarkerMapper.insertCustom(marker);
				
				result.setIsSuccess();
				result.setAccountId(newAccountId);
				
			} else {
				String emptyValue = tmpBuffer.toString();
				result.setMessage(emptyValue);
			}
			
			
		} catch (Exception e) {
			log.error("account regist error %s", e.toString());
			result.setMessage(e.toString());
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}

	@Override
	public CommonResult accountNumberDuplicateCheck(AccountNumberDuplicateCheckDTO dto) {
		
		// 后台过滤  仅保留输入字符串中的数字
		String accountNumberInput = dto.getAccountNumber().replaceAll("[^\\d]", "");
		
		CommonResult result = new CommonResult();
		
		if (accountNumberInput.length() > AccountInfoConstant.ACCOUNT_NUMBER_LENGTH_MAX || 
				accountNumberInput.length() < AccountInfoConstant.ACCOUNT_NUMBER_LENGTH_MIN) {
			
			result.setMessage("Please fill in correct account number.");
			
		} else {
			
			List<String> accountNumberList = accountInfoMapper.accountNumberDuplicateCheck(accountNumberInput);
			if (accountNumberList.isEmpty()) {
				result.normalSuccess();
			} else {
				result.setMessage("This account number was exist.");
			}
		}
		
	    return result;
	}
	
	private CommonResult accountNumberDuplicateCheck(String accountNumber) {
		AccountNumberDuplicateCheckDTO dto = new AccountNumberDuplicateCheckDTO();
		dto.setAccountNumber(accountNumber);
		return accountNumberDuplicateCheck(dto);
	}

	@Override
	public List<String> findAccountNumberListByCondition(FindAccountInfoByConditionDTO dto) {
		List<AccountInfo> accountList = findByCondition(dto);
		if(accountList == null || accountList.size() < 1) {
			return new ArrayList<String>();
		}
		
		List<String> result = accountList.stream().map(AccountInfo::getAccountNumber).collect(Collectors.toList());
		return result;
	}
	
	@Override
	public List<AccountInfo> findAccountsByCondition(FindAccountInfoByConditionDTO dto) {
		return findByCondition(dto);
	}
	
	@Override
	public GetAccountNumberAndAliasListResult findCurrentAccountNumberListByCondition(FindAccountInfoByConditionDTO dto) {
		GetAccountNumberAndAliasListResult result = new GetAccountNumberAndAliasListResult();
		List<AccountInfo> accountList = findCurrentAccountInfoListByCondition(dto);
		if(accountList == null || accountList.size() < 1) {
			return result;
		}
		
		AccountNumberWithAliasBO bo = null;
		List<AccountNumberWithAliasBO> boList = new ArrayList<AccountNumberWithAliasBO>();
		for(AccountInfo account : accountList) {
			bo = new AccountNumberWithAliasBO();
			bo.setAccountId(account.getAccountId());
			bo.setAccountNumber(account.getAccountNumber());
			bo.setAlias(account.getAccountAlias());
			boList.add(bo);
		}
		result.setAccountList(boList);
		result.setIsSuccess();
		return result;
	}
	
	private List<AccountInfo> findByCondition(FindAccountInfoByConditionDTO dto) {
		return accountInfoMapper.findByCondition(dto);
	}
	
	@Override
	public List<AccountInfo> findCurrentAccountInfoListByCondition(FindAccountInfoByConditionDTO dto) {
		AccountHolder holder = accountHolderController.getDefaultHolder();
		if(holder == null) {
			return new ArrayList<AccountInfo>();
		}
		dto.setAccountHolderId(holder.getAccountHolderId());
		return accountInfoMapper.findByCondition(dto);
	}
	
	@Override
	public List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByCondition(Long holderId, GetAccountListByConditionParam cp) {
		GetAccountInfoWithConditionParam mp = new GetAccountInfoWithConditionParam();
		mp.setHolderId(holderId);
		mp.setBankId(cp.getBankId());
		mp.setAccountType(cp.getAccountType());
		List<AccountInfoWithBankInfo> accountInfoList = accountInfoMapper.getAccountInfoWithBankInfo(mp);
		accountInfoList = accountUsedQuotaStatistics(accountInfoList);
		return accountInfoList;
	}
	
	/**
	 * 信用卡类统计统计方法;
	 * 按相同银行统计出已用额度
	 * 
	 * @param accountInfoList
	 * @return
	 */
	@Override
	public List<AccountInfoWithBankInfo> accountUsedQuotaStatistics(List<AccountInfoWithBankInfo> accountInfoList) {
		if(accountInfoList == null || accountInfoList.size() <= 0) {
			return accountInfoList;
		}
		
		// 按银行ID 整理出负债汇总(不算溢存款),各自的最高授信额度
		List<AccountStatisticsByBankIdBO> statisticsList = accountInfoStatisticsService.accountStatisticsByBankId(accountInfoList);
		
		setAvaliableQuotaByStatisticResult(statisticsList, accountInfoList);
		
		return accountInfoList;
	}
	
	private void setAvaliableQuotaByStatisticResult(List<AccountStatisticsByBankIdBO> statisticsList, List<AccountInfoWithBankInfo> accountInfoList) {
		BigDecimal thisAccountAvaliableQuota;
		BigDecimal thisCreditQuota;
		BigDecimal thisBankRemainingQuota;
		Long thisBankId;
		AccountStatisticsByBankIdBO thisStatisticsResult; 
		for(AccountInfoWithBankInfo account : accountInfoList) {
			if(AccountType.creditAccount.getCode().equals(account.getAccountType())) {
				thisBankId = account.getBankId();
				thisStatisticsResult = accountInfoStatisticsService.getStatisticsByBankId(statisticsList, thisBankId);
				thisCreditQuota = account.getTotalCreditQuota();
				thisBankRemainingQuota = thisStatisticsResult.getRemainingQuota();
				
				// 不考虑溢存款,仅先统计信用额度范围.如果余额为负,则先计算出此卡单独可用额度.
				if(account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
					thisAccountAvaliableQuota = thisCreditQuota.add(account.getAccountBalance());
				} else {
					thisAccountAvaliableQuota = thisCreditQuota;
				}
				
				// 因为同银行下,多个银行卡授信额度共享,故(卡片单独可用额度||银行授信额度)中取低值
				if(thisAccountAvaliableQuota.compareTo(thisBankRemainingQuota) <= 0) {
					account.setAvaliableQuota(thisAccountAvaliableQuota);
				} else {
					account.setAvaliableQuota(thisBankRemainingQuota);
				}
			
				// 如果卡片中有溢存款,在刚才得出的可用额度中,添加溢存款金额.
				if(account.getAccountBalance().compareTo(BigDecimal.ZERO) > 0) {
					account.setAvaliableQuota(account.getAvaliableQuota().add(account.getAccountBalance()));	
				}
			}
		}
	}
	
	@Override
	public String getMainAccountNum(int id) {
		return accountInfoMapper.getMainAccountNum(id);
	}
	
	@Override
	public boolean accountMarkerVerify(AccountInfo accountInfo) {
		AccountInfoMarker marker = accountInfoMarkerMapper.getMarkerByAccountId(accountInfo.getAccountId());
		if(marker == null) {
			return false;
		} 
		return accountInfoBaseVerifier(marker, accountInfo);
	}

	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public InsertTransationResult insertTradingRecorderSelective(InsertNewTransationDTO p) throws Exception {
		InsertTransationResult result = new InsertTransationResult();
		
		String accountNumber = p.getAccountNumber();
		TransationType transationType = null;
		
		if(p.getTransationType() == null 
				|| (transationType = TransationType.getType(p.getTransationType())) == null 
				|| !checkAccountNumberBelongUser(accountNumber)) {
			result.failWithMessage("缺失交易类型,或者账号异常");
			return result;
		}
		
		AccountInfo targetAccount = accountInfoMapper.getAccountInfoByAccountNumber(accountNumber);
		if(null == targetAccount) {
			result.failWithMessage("账号异常");
			return result;
		}
		
		BigDecimal transationAmount = null;
		if (TransationType.transationTypeFix.equals(transationType)) {
			try {
				if(AccountType.creditAccount.getCode().equals(targetAccount.getAccountType())) {
					// 如果是贷记卡类账户,可直接输入需要冲正的可用额度
					if(p.getFixCreditQuota() != null) {
						BigDecimal targetQuota = p.getFixCreditQuota();
						targetQuota = targetQuota.setScale(0, RoundingMode.HALF_UP);
						transationAmount = targetQuota.subtract(targetAccount.getAccountBalance()).subtract(targetAccount.getTotalCreditQuota());
					}
				} else if(AccountType.debitAccount.getCode().equals(targetAccount.getAccountType())) {
					BigDecimal targetAmount = null;
					targetAmount = p.getTransationAmount();
					targetAmount = targetAmount.setScale(2, RoundingMode.HALF_UP);
					transationAmount = targetAmount.subtract(targetAccount.getAccountBalance());
				} else {
					result.failWithMessage("账号类型异常");
					return result;
				}
				
				p.setTransationAmount(transationAmount);
				p.setTransationType(TransationType.transationTypeIncome.getCode());
				if(StringUtils.isBlank(p.getRemark())) {
					p.setRemark(TransationType.transationTypeFix.getName());
				}
				if(StringUtils.isBlank(p.getTransationParties())) {
					p.setTransationParties(TransationType.transationTypeFix.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.failWithMessage(e.getMessage());
				return result;
			}
		} 
		
		if(!accountMarkerVerify(targetAccount)) {
			result.failWithMessage("账户被篡改");
			return result;
		}
		
		InsertTradingRecorderResult InsertTradingRecordResult = tradingController.insertTradingRecorderSelective(p, targetAccount.getAccountId());
		
		TradingRecorder tradingRecord = tradingController.getTradingRecorderById(InsertTradingRecordResult.getNewTradingId());
		
		transationAmount = tradingRecord.getAmount();
		
		if (InsertTradingRecordResult.getNewTradingId() != null && InsertTradingRecordResult.getNewTradingId() > 0) {
			
			if(updateAccountAmount(targetAccount, transationAmount)) {
				result.normalSuccess();
				result.setAccountNumber(accountNumber);
				result.setTransationAmount(transationAmount);
				result.setTransationDate(p.getTransationDate());
				result.setTransationParties(p.getTransationParties());
				return result;
			} else {
				throw new Exception();
			}
			
		} else {
			throw new Exception();
		}
		
	}
	
	@Override
	public boolean checkAccountNumberBelongUser(String accountNumber) {
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();
		if(null == holderList || holderList.size() == 0 || null == accountNumber || accountNumber.length() == 0) {
			return false;
		}
		
		HashMap<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("accountNumber", accountNumber);
		conditionMap.put("accountHolderId", String.valueOf(holderList.get(0).getAccountHolderId()));
		
		Integer result = accountInfoMapper.checkAccountNumberBelongUser(conditionMap);
		if(result == 1) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean checkAccountBelongUserByTailNumber(String accountTailNumber) {
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();
		if(null == holderList || holderList.size() == 0 || null == accountTailNumber || accountTailNumber.length() == 0) {
			return false;
		}
		
		HashMap<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("accountTailNumber", accountTailNumber);
		conditionMap.put("accountHolderId", String.valueOf(holderList.get(0).getAccountHolderId()));
		
		Integer result = accountInfoMapper.checkAccountNumberBelongUser(conditionMap);
		if(result == 1) {
			return true;
		}
		
		return false;
	}
	
	private boolean accountNumberCheck(String accountNumber) {
		return (StringUtils.isNotEmpty(accountNumber) && accountNumber.matches("\\d{16,19}"));
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public int updateAccountMarker(String accountNumber) {
		AccountInfo targetAccount = accountInfoMapper.getAccountInfoByAccountNumber(accountNumber);
		AccountInfoMarker marker = accountInfoMarkerMapper.getMarkerByAccountId(targetAccount.getAccountId());
		if(targetAccount == null || marker == null) {
			return 0;
		}
		marker.setMarker(createAccountInfoMarker(targetAccount));
		return accountInfoMarkerMapper.updateAccountMarker(marker);
	}

	@Override
	public AccountInfoWithBankInfo getAccountInfoWithBankInfoByAccountNumber(String accountNumber) {
		if(StringUtils.isBlank(accountNumber)) {
			return new AccountInfoWithBankInfo();
		}
		GetAccountInfoWithConditionParam mapperParam = new GetAccountInfoWithConditionParam();
		mapperParam.setAccountNumber(accountNumber);
		List<AccountInfoWithBankInfo> accountList = accountInfoMapper.getAccountInfoWithBankInfo(mapperParam);
		
		if(accountList != null && !accountList.isEmpty()) {
			return accountList.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<AccountInfoWithBankInfo> getAccountInfoWithBankInfoByHolderId(Long holderId) {
		if(holderId == null) {
			return new ArrayList<AccountInfoWithBankInfo>();
		}
		GetAccountInfoWithConditionParam mapperParam = new GetAccountInfoWithConditionParam();
		mapperParam.setHolderId(0L + holderId);
		return accountInfoMapper.getAccountInfoWithBankInfo(mapperParam);
	}
	
	
	@Override
	public List<SummaryAccountsByBankId> sumAccountList(List<AccountInfoWithBankInfo> accountList) {
		
		HashMap<Long, ArrayList<AccountInfoWithBankInfo>> sumMap = new HashMap<Long, ArrayList<AccountInfoWithBankInfo>>(); 
//		HashMap<Integer, > resultMap = new HashMap<Integer, SummaryAccountsByBankId>();
		List<SummaryAccountsByBankId> resultList = new ArrayList<SummaryAccountsByBankId>();
		ArrayList<AccountInfoWithBankInfo> tmpAccountList = null;
		
		for(AccountInfoWithBankInfo accountInfo : accountList) {
			if(sumMap.keySet().contains(accountInfo.getBankId())) {
				sumMap.get(accountInfo.getBankId()).add(accountInfo);
				
			} else {
				tmpAccountList = new ArrayList<AccountInfoWithBankInfo>();
				tmpAccountList.add(accountInfo);
				sumMap.put(accountInfo.getBankId(), tmpAccountList);
				
			}
		}
		
		SummaryAccountsByBankId mainSum = new SummaryAccountsByBankId();
		SummaryAccountsByBankId subSum = new SummaryAccountsByBankId();
		for(Map.Entry<Long, ArrayList<AccountInfoWithBankInfo>> entry : sumMap.entrySet()) {
			resultList.add(subSum = summaryByBankId(entry.getValue()));
			subSum.subSum();
			mainSum.addCreditQuota(subSum.getMaxCreditQuota());
			mainSum.addMaxCreditQuota(subSum.getMaxCreditQuota());
			mainSum.addSpentCredit(subSum.getSumSpentCreditQuota());
			mainSum.addOverDeposit(subSum.getSumOverDeposit());
			mainSum.addDeposit(subSum.getSumDeposit());
			subSum = new SummaryAccountsByBankId();
		}
		mainSum.subSum();
		resultList.add(0, mainSum);
		
		return resultList;
	}
	
	private SummaryAccountsByBankId summaryByBankId(List<AccountInfoWithBankInfo> accounts) {
		
		SummaryAccountsByBankId result = new SummaryAccountsByBankId();
		
		for(AccountInfoWithBankInfo account : accounts) {
			
			result.setBankId(account.getBankId());
			result.setBankChineseName(account.getBankChineseName());
			
			if(AccountType.debitAccount.getCode().equals(account.getAccountType())) {
				result.addDeposit(account.getAccountBalance());
				
			} else if(AccountType.creditAccount.getCode().equals(account.getAccountType()) ||
					AccountType.stagingAccountSpentQuota.getCode().equals(account.getAccountType()) ||
					AccountType.stagingAccountNotSpentQuota.getCode().equals(account.getAccountType()) ) {
				result.addMaxCreditQuota(account.getCreditsQuota());
				
				if(account.getAccountBalance().compareTo(BigDecimal.ZERO) > 0) {
					result.addOverDeposit(account.getAccountBalance());
				} else {
					result.addSpentCredit(account.getAccountBalance());
				}
			}
			
		}
		return result;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public int modifyAccountInfoTemproraryCreditsVaildDate(String temproraryCreditsVaildDate, String accountNumber, boolean isAdmin) {
		if(!checkAccountNumberBelongUser(accountNumber) || !dateHandler.isDateValid(temproraryCreditsVaildDate)){
			return 0;
		}
		
		Date newTemproraryCreditsVaildDate = dateHandler.stringToDateUnkonwFormat(temproraryCreditsVaildDate);
		if(newTemproraryCreditsVaildDate.before(new Date()) && !isAdmin) {
			return 0;
		}
		
		AccountInfo targetAccount = new AccountInfo();
		targetAccount.setTemproraryCreditsVaildDate(newTemproraryCreditsVaildDate);
		targetAccount.setAccountNumber(accountNumber);
		
		int modifyCount = accountInfoMapper.modifyAccountInfo(targetAccount);
		if(modifyCount == 1) {
			updateAccountInfoMarker(targetAccount);
		}
		
		return modifyCount;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResultCX modifyCreditsQuota(ModifyCreditsQuotaDTO dto) {
		CommonResultCX r = new CommonResultCX();
		if(dto.getAccountNumber() == null || dto.getNewCreditsQuota() == null || !checkAccountNumberBelongUser(dto.getAccountNumber())){
			return r;
		}
		
		AccountInfo targetAccount = getAccountInfoByAccountNumber(dto.getAccountNumber());
		if(!accountMarkerVerify(targetAccount)) {
			return r;
		}
		targetAccount.setCreditsQuota(dto.getNewCreditsQuota().setScale(2, RoundingMode.HALF_UP));
		
		int modifyCount = accountInfoMapper.modifyAccountInfo(targetAccount);
		
		if(modifyCount == 1) {
			if(updateAccountInfoMarker(targetAccount)) {
				r.setMessage("modify credits quota to: " + dto.getNewCreditsQuota());
				r.setIsSuccess();
			} else {
				return r;
			}
		}
		
		return r;
	}
	
	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public int modifyTemproraryCreditsQuota(String newTemproraryCreditsQuota, String accountNumber) {
		if(!checkAccountNumberBelongUser(accountNumber) || !numberUtil.matchInteger(newTemproraryCreditsQuota)){
			return 0;
		}
		
		AccountInfo targetAccount = new AccountInfo();
		targetAccount.setAccountNumber(accountNumber);
		targetAccount.setTemproraryCreditsQuota(new BigDecimal(newTemproraryCreditsQuota));
		
		int modifyCount = accountInfoMapper.modifyAccountInfo(targetAccount);
		if(modifyCount == 1) {
			updateAccountInfoMarker(targetAccount);
		}
		
		return modifyCount;
	}
	
	@Override
	public List<AccountInfo> getAllAffiliatedAccountByAffiliationId(String accountNumber) {
		
		if(!checkAccountNumberBelongUser(accountNumber) || !baseUtilCustom.hasAdminRole()) {
			return null;
		} 
		
		AccountInfo masterAccount = accountInfoMapper.getAccountInfoByAccountNumber(accountNumber);
		
		if(masterAccount == null) {
			return null;
		} else {
			return accountInfoMapper.getAllAffiliatedAccountByAffiliationId(masterAccount.getAccountAffiliation());
		}
		
	}

	@Override
	public List<AccountInfo> getAllAffiliatedAccountByAffiliationId(Long accountAffiliatioId) {
		
		AccountInfo masterAccount = accountInfoMapper.getAcountInfoById(accountAffiliatioId);
		
		if(masterAccount == null) {
			return null;
		}
		
		if(!checkAccountNumberBelongUser(masterAccount.getAccountNumber())) {
			return null;
		}
		
		return accountInfoMapper.getAllAffiliatedAccountByAffiliationId(accountAffiliatioId);
		
	}
	
	@Override
	public HashMap<String, String> sumAffiliatedAccountInfo(List<AccountInfo> affiliatedAccounts) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		BigDecimal sumBalance = new BigDecimal(0);
		for(AccountInfo subAccount : affiliatedAccounts) {
			sumBalance = sumBalance.add(subAccount.getAccountBalance());
		}
		
		resultMap.put("balance", sumBalance.toString());
		
		return resultMap;
	}
	
	@Override
	public AccountInfo getAccountInfoByAccountNumber(String accountNumber) {
		if(!numberUtil.matchPositiveInteger(accountNumber)) {
			return null;
		}
		return accountInfoMapper.getAccountInfoByAccountNumber(accountNumber);
	}
	
	private boolean updateAccountInfoMarker(AccountInfo accountInfo) {
		AccountInfoMarker newMarker = new AccountInfoMarker();
		newMarker.setAccountId(accountInfo.getAccountId());
		newMarker.setMarker(createAccountInfoMarker(accountInfo));
		
		int accountMarkerModifyCount = 0;
		accountMarkerModifyCount = accountInfoMarkerMapper.updateAccountMarker(newMarker);
		
		if(accountMarkerModifyCount == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean updateAccountAmount(AccountInfo targetAccount, BigDecimal transationAmount) {
		targetAccount.setAccountBalance(targetAccount.getAccountBalance().add(transationAmount));
		
		if (accountInfoMapper.accountAmountModify(targetAccount) == 1 && updateAccountInfoMarker(targetAccount)) {
			return true ;
		} else {
			return false;
		}
	}

	
	@Override
	public GetAccountListResult accountInfoWithBankInfoList(GetAccountListByConditionParam param) {
		GetAccountListResult result = new GetAccountListResult();
		List<AccountHolder> holderList = accountHolderController.getCurrentHolders();
		
		List<AccountInfoWithBankInfo> accountList = getAccountInfoWithBankInfoByCondition(0L + holderList.get(0).getAccountHolderId(), param);
		result.setIsSuccess();
		result.setAccountList(accountList);
		return result;
	}
	
	
	private String createAccountInfoMarker(AccountInfo accountInfo) {
        return encryptUtil.Sha1(encryptUtil.Sha1(encryptUtil.ToMd5String(getAccountInfoInfos(accountInfo))));
    }
	
	private String getAccountInfoInfos(AccountInfo po) {
		return "" + po.getAccountId() + po.getAccountHolderId() 
				+ po.getAccountNumber() + po.getAccountAlias() + po.getAccountType() + po.getAccountAffiliation()
				+ po.getVaildDate() + po.getBankId() + po.getBankUnionId()
				+ po.getAccountBalance() + po.getCreditsQuota() + po.getTemproraryCreditsQuota() + po.getTemproraryCreditsVaildDate() 
				+ po.getRemark() + po.getIsDelete() + po.getCreateTime();
	}
    
    @SuppressWarnings("unused")
	private boolean checkMarker(AccountInfoMarker marker, AccountInfo accountInfo) {
        
        if(StringUtils.isBlank(marker.getMarker())) {
            return false;
        }
        
        return marker.getMarker().equals(createAccountInfoMarker(accountInfo));
    }
    
	private boolean checkVaildDate(AccountInfo accountInfo) {
        
        if(accountInfo.getVaildDate() == null) {
            return false;
        }
        
        if(accountInfo.getVaildDate().after(new Date())) {
            return true;
        }
        
        return false;
    }
    
    private boolean accountInfoBaseVerifier(AccountInfoMarker marker, AccountInfo accountInfo) {
    	/*
    	 * 2020-09-07
    	 * 基本上只有私用 暂时搁置账户验证
    	 */
//        return checkVaildDate(accountInfo) && checkMarker(marker, accountInfo);
    	return checkVaildDate(accountInfo);
    }
    
    @Override
    @Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
    public CommonResult modifyAccountInfoVaildDate(ModifyValidDateDTO dto) throws Exception {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isAnyBlank(dto.getAccountNumber(), dto.getNewVaildDate())) {
			r.failWithMessage("null param");
			return r;
		}
		
		if(!checkAccountNumberBelongUser(dto.getAccountNumber()) || !dateHandler.isDateValid(dto.getNewVaildDate())){
			r.failWithMessage("param error");
			return r;
		}
		
		Date newVaildDate = dateHandler.stringToDateUnkonwFormat(dto.getNewVaildDate());
		if(newVaildDate.before(new Date()) && !isBigUser()) {
			r.failWithMessage("date error");
			return r;
		}
		
		
		int modifyCount = accountInfoMapper.modifyAccountInfoVaildDate(newVaildDate, dto.getAccountNumber());
		if(modifyCount == 1) {
			updateAccountMarker(dto.getAccountNumber());
			r.successWithMessage("modify vaild date to: " + dto.getNewVaildDate());
		} else {
			throw new Exception("");
		}
		
		return r;
		
    }
}
