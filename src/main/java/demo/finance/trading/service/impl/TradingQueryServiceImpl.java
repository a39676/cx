package demo.finance.trading.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateTimeHandle;
import demo.baseCommon.service.CommonService;
import demo.finance.account_info.controller.AccountInfoController;
import demo.finance.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.bank.controller.BankInfoController;
import demo.finance.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.finance.bank.pojo.po.BankInfo;
import demo.finance.bank.pojo.result.FindBankInfoResult;
import demo.finance.trading.mapper.TradingRecorderMapper;
import demo.finance.trading.pojo.dto.SelectTradingRecordBO;
import demo.finance.trading.pojo.dto.TradingRecorderDTO;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.result.TradingQueryResult;
import demo.finance.trading.pojo.result.TradingQuerySubResult;
import demo.finance.trading.service.TradingQueryService;

@Service
public class TradingQueryServiceImpl extends CommonService implements TradingQueryService {
	
	@Autowired
	private AccountInfoController accountInfoController;
	@Autowired
	private BankInfoController bankController;
	@Autowired
	private TradingRecorderMapper tradingMapper;
	
	@Override
	public TradingQueryResult findTradingRecordByCondition(TradingRecorderDTO dto) {
		/*
		 * should check account owner
		 */
		TradingQueryResult r = new TradingQueryResult();
		List<AccountInfo> accounts = findAcconts(dto);
		Map<Long, AccountInfo> accountMap = accounts.stream().collect(Collectors.toMap(AccountInfo::getAccountId, p -> p));
		
		Map<Long, BankInfo> bankMap = findBanks(accounts);
		
		SelectTradingRecordBO bo = buildTradingRecorderBO(dto, accounts);
		List<TradingRecorder> recordList = tradingMapper.selectTradingRecord(bo);

		List<TradingQuerySubResult> tradingRecordList = new ArrayList<TradingQuerySubResult>();
		
		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal totalPay = BigDecimal.ZERO;
		for(TradingRecorder i : recordList) {
			tradingRecordList.add(buildTradingQuerySubResult(i, accountMap, bankMap));
			if(i.getAmount().compareTo(BigDecimal.ZERO) > 0) {
				totalIncome.add(i.getAmount());
			} else {
				totalPay.add(i.getAmount());
			}
		}
		r.setTotalIncome(totalIncome);
		r.setTotalPay(BigDecimal.ZERO.subtract(totalPay));
		r.setTotalAmount(totalIncome.subtract(totalPay));
		r.setTradingSubResultList(tradingRecordList);
		
		r.setIsSuccess();
		
		return r;
	}
	
	private List<AccountInfo> findAcconts(TradingRecorderDTO dto) {
		FindAccountInfoByConditionDTO findAccountDTO = new FindAccountInfoByConditionDTO();
		if(dto.getAccountId() != null) {
			findAccountDTO.setAccountId(dto.getAccountId());
		} else if(StringUtils.isNotBlank(dto.getAccountNumber())) {
			findAccountDTO.setAccountNumber(dto.getAccountNumber());
			
			findAccountDTO.setBankId(dto.getBankId());
			findAccountDTO.setBankUnionId(dto.getBankUnionId());
			
		}
		return accountInfoController.findAccountsByCondition(findAccountDTO);
	}
	
	private SelectTradingRecordBO buildTradingRecorderBO(TradingRecorderDTO dto, List<AccountInfo> accounts) {
		dto.setPageParam();
		SelectTradingRecordBO bo = new SelectTradingRecordBO();
		
		if(accounts != null && accounts.size() > 0) {
			if(accounts.size() == 1) {
				bo.setAccountId(dto.getAccountId());
			} else {
				List<Long> accountIds = new ArrayList<Long>();
				for(AccountInfo i : accounts) {
					accountIds.add(i.getAccountId());
				}
				bo.setAccountIdList(accountIds);
			}
		}
		
		bo.setEndTime(dto.getEndTime());
		bo.setStartTime(dto.getStartTime());
		bo.setIncludeRedCancelOut(dto.getIncludeRedCancelOut());
		bo.setMaxAmount(dto.getMaxAmount());
		bo.setMinAmount(dto.getMinAmount());
		return bo;
	}

	private TradingQuerySubResult buildTradingQuerySubResult(TradingRecorder po, Map<Long, AccountInfo> accountMap, Map<Long, BankInfo> bankMap) {
		TradingQuerySubResult r = new TradingQuerySubResult();
		AccountInfo account = accountMap.get(po.getAccountId());
		r.setAccountAlias(account.getAccountAlias());
		r.setAccountNumber(account.getAccountNumber());
		r.setAmount(po.getAmount());
		r.setBankName(bankMap.get(account.getBankId()).getBankChineseNameShort());
		r.setTradingDate(DateTimeHandle.dateToLocalDateTime(po.getTransationDate()));
		r.setTransationParties(po.getTransationParties());
		
		return r;
	}
	
	private Map<Long, BankInfo> findBanks(List<AccountInfo> accounts) {
		FindBankInfoParam cp = new FindBankInfoParam();
		FindBankInfoResult banks = bankController.getBankInfoByCondition(cp);
		Map<Long, BankInfo> bankMap = banks.getBankList().stream().collect(Collectors.toMap(BankInfo::getBankId, b -> b));
		return bankMap;
	}

}
