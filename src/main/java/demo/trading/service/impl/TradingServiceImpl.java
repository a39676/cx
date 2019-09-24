package demo.trading.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dateTimeHandle.DateTimeHandle;
import dateTimeHandle.DateUtilCustom;
import demo.account_info.controller.AccountInfoController;
import demo.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.account_info.pojo.po.AccountInfo;
import demo.bank.controller.BankInfoController;
import demo.bank.pojo.param.controllerParam.FindBankInfoParam;
import demo.bank.pojo.po.BankInfo;
import demo.bank.pojo.result.FindBankInfoResult;
import demo.baseCommon.pojo.param.controllerParam.InsertNewTransationParam;
import demo.baseCommon.pojo.type.TransationType;
import demo.baseCommon.service.CommonService;
import demo.trading.mapper.HolderCommonTransationCustomMapper;
import demo.trading.mapper.TradingRecorderCustomMapper;
import demo.trading.mapper.TradingRecorderMarkerMapper;
import demo.trading.pojo.CommonTransationParties;
import demo.trading.pojo.dto.SelectTradingRecordBO;
import demo.trading.pojo.dto.TradingRecorderDTO;
import demo.trading.pojo.po.TradingRecorder;
import demo.trading.pojo.po.TradingRecorderMarker;
import demo.trading.pojo.result.InsertTradingRecorderResult;
import demo.trading.pojo.result.TradingQueryResult;
import demo.trading.pojo.result.TradingQuerySubResult;
import demo.trading.service.TradingService;
import ioHandle.FileUtilCustom;
import numericHandel.NumericUtilCustom;

@Service
public class TradingServiceImpl extends CommonService implements TradingService {

	@Autowired
	private AccountInfoController accountInfoController;
	@Autowired
	private BankInfoController bankController;
	@Autowired
	private TradingRecorderCustomMapper tradingMapper;
	@Autowired
	private TradingRecorderMarkerMapper tradingMarkerMapper;
	@Autowired
	private HolderCommonTransationCustomMapper holderCommonTransationCustomMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	private boolean insertTradingRecorderMarker(TradingRecorder tradingRecorder) {
		TradingRecorderMarker marker = new TradingRecorderMarker();
		marker.setTradingId(tradingRecorder.getTradingId());
		marker.setCreateTime(tradingRecorder.getCreateTime());
		marker.setMarker(tradingRecorder.getInfos());

		if (tradingMarkerMapper.insert(marker) == 1) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public InsertTradingRecorderResult insertTradingRecorder(InsertNewTransationParam p, Long accountId) {
		InsertTradingRecorderResult result = new InsertTradingRecorderResult();
		if (p == null || accountId == null) {
			result.normalFail();
			return result;
		}
		Long newTradingRecorderId = snowFlake.getNextId();
		TradingRecorder tradingRecorder = buildTradingRecorderFrom(newTradingRecorderId, p, accountId);
		if (tradingRecorder == null) {
			result.normalFail();
			return result;
		}

		tradingMapper.isnertTradingRecorder(tradingRecorder);
		insertTradingRecorderMarker(tradingRecorder);

		result.setNewTradingId(newTradingRecorderId);
		result.setIsSuccess();
		return result;
	}

	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public long insertTradingRecorderFromFileLine(String strLineInput, AccountInfo accountInfo) {

		TradingRecorder tradingRecorder = stringLineToTradingRecorder(strLineInput, accountInfo);
		if (tradingRecorder == null) {
			return 0L;
		}

		tradingMapper.isnertTradingRecorder(tradingRecorder);
		insertTradingRecorderMarker(tradingRecorder);

		accountInfoController.updateAccountAmount(accountInfo, tradingRecorder.getAmount());

		return tradingRecorder.getTradingId();
	}

	@Override
	public TradingRecorder getTradingRecordById(Long tradingRecorderId) {
		return tradingMapper.getTradingRecordById(tradingRecorderId);
	}

	@Override
	public List<CommonTransationParties> getCurrentCommonTransation(Long holderId, Integer limit) {
		if (limit == null) {
			limit = 10;
		}

		List<CommonTransationParties> commonTransationList = holderCommonTransationCustomMapper
				.getCurrentCommonTransation(holderId, limit);

		if (commonTransationList == null) {
			return new ArrayList<CommonTransationParties>();
		} else {
			return commonTransationList;
		}
	}

	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public String importTradingRecordFromFiles(String tradingRecordTxtPath) {
		File mainFolder = new File(tradingRecordTxtPath);
		if (!mainFolder.isDirectory()) {
			return "not a folder";
		}

		StringBuffer result = new StringBuffer();

		File[] files = mainFolder.listFiles();
//		List<File> fileList = new ArrayList<File>();
		for (File tmpFile : files) {
			if (tmpFile.isFile() && tmpFile.getName().endsWith(".csv")
					&& tmpFile.getName().substring(0, tmpFile.getName().lastIndexOf(".")).matches("\\d{16,19}")) {
				result.append(importTradingRecordFromFile(tmpFile, ioUtil));
			}
		}

		return result.toString();
	}

	private String importTradingRecordFromFile(File subFile, FileUtilCustom fileHandle) {
		String accountNumber = subFile.getName().substring(0, subFile.getName().lastIndexOf("."));
		boolean isBelong = accountInfoController.checkAccountNumberBelongUser(accountNumber);
		if (!isBelong) {
			return accountNumber + " was not your account, trading record didn`t import \n";
		}

		AccountInfo accountInfo = accountInfoController.getAccountInfoByAccountNumber(accountNumber);

		StringBuffer result = new StringBuffer();
		result.append(accountNumber + ": \n");

		String str = fileHandle.getStringFromFile(subFile.getAbsolutePath());
		System.out.println(str.split("\n")[0]);
		String[] lines = str.split("\n");
		if (lines.length <= 0) {
			return result.toString();
		}
		for (String line : lines) {
			if (!line.startsWith("#")) {
				if (insertTradingRecorderFromFileLine(line, accountInfo) > 0) {
					result.append(line + "\t" + "insert" + "\n");
				} else {
					result.append(line + "\t" + "not insert" + "\n");
				}
			}
		}

		return result.toString();
	}

	// 2019-01-29 规范化迁移 从 TradingRecorderBuildAndVerifier 收入
	/*
	 * private TradingRecorder jsonToTradingRecorder(JSONObject jsonInput) {
	 * 
	 * Date now = new Date();
	 * 
	 * Date transationDate =
	 * DateUtilCustom.stringToDateUnkonwFormat(String.valueOf(jsonInput.get(
	 * "transationDate"))); String transationParties =
	 * String.valueOf(jsonInput.get("transationParties")); BigDecimal
	 * transationAmount = null; String accountNumber =
	 * String.valueOf(jsonInput.get("accountNumber"));
	 * if(!NumericUtilCustom.matchPositiveInteger(accountNumber)) { return null; }
	 * 
	 * try {
	 * if(jsonInput.get("transationType").equals(TransationType.transationTypePay.
	 * getCode())) { transationAmount = BigDecimal.ZERO.subtract(new
	 * BigDecimal(String.valueOf(jsonInput.get("transationAmount"))));
	 * 
	 * } else if
	 * (jsonInput.get("transationType").equals(TransationType.transationTypeIncome.
	 * getCode())) { transationAmount = new
	 * BigDecimal(String.valueOf(jsonInput.get("transationAmount")));
	 * 
	 * } else { return null; } } catch (Exception e) { e.printStackTrace(); return
	 * null; }
	 * 
	 * 
	 * TradingRecorder tradingRecorder = new TradingRecorder();
	 * tradingRecorder.setAccountId(Long.parseLong(String.valueOf(jsonInput.get(
	 * "accountId")))); tradingRecorder.setAccountNumber(accountNumber);
	 * tradingRecorder.setAmount(transationAmount);
	 * tradingRecorder.setTransationDate(transationDate);
	 * tradingRecorder.setTransationParties(transationParties);
	 * tradingRecorder.setCreateTime(now);
	 * tradingRecorder.setRemark(String.valueOf(jsonInput.get("remark")));
	 * 
	 * return tradingRecorder; }
	 */

	private TradingRecorder buildTradingRecorderFrom(Long newTradingRecordId, InsertNewTransationParam p,
			Long accountId) {

		if (p.getTransationAmount() == null) {
			return null;
		}

		Date now = new Date();
		Date transationDate = DateUtilCustom.stringToDateUnkonwFormat(p.getTransationDate());
		String transationParties = p.getTransationParties();
		BigDecimal transationAmount = null;
		String accountNumber = p.getAccountNumber();
		if (!NumericUtilCustom.matchPositiveInteger(accountNumber)) {
			return null;
		}

		TransationType transationType = TransationType.getType(p.getTransationType());

		try {
			if (transationType == null) {
				return null;
			}

			if (TransationType.transationTypePay.equals(transationType)) {
				transationAmount = BigDecimal.ZERO.subtract(p.getTransationAmount());
			} else if (TransationType.transationTypeIncome.equals(transationType)) {
				transationAmount = p.getTransationAmount();
			}
		} catch (Exception e) {
			return null;
		}

		TradingRecorder tradingRecorder = new TradingRecorder();
		tradingRecorder.setTradingId(newTradingRecordId);
		tradingRecorder.setAccountId(accountId);
		tradingRecorder.setAccountNumber(accountNumber);
		tradingRecorder.setAmount(transationAmount);
		tradingRecorder.setTransationDate(transationDate);
		tradingRecorder.setTransationParties(transationParties);
		tradingRecorder.setCreateTime(now);
		tradingRecorder.setRemark(p.getRemark());

		return tradingRecorder;
	}

	private TradingRecorder stringLineToTradingRecorder(String strInput, AccountInfo accountInfo) {

		Date now = new Date();

		String[] line = strInput.split(",");
		if (line.length < 2) {
			return null;
		}

		Date transationDate = DateUtilCustom.stringToDateUnkonwFormat(String.valueOf(line[0]));
		if (transationDate == null) {
			return null;
		}

		BigDecimal transationAmount = null;

		try {
			transationAmount = new BigDecimal(String.valueOf(line[1]));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String transationParties = String.valueOf(line[2]);

		TradingRecorder tradingRecorder = new TradingRecorder();
		tradingRecorder.setTradingId(snowFlake.getNextId());
		tradingRecorder.setAccountId(accountInfo.getAccountId());
		tradingRecorder.setAccountNumber(accountInfo.getAccountNumber());
		tradingRecorder.setAmount(transationAmount);
		tradingRecorder.setTransationDate(transationDate);
		tradingRecorder.setTransationParties(transationParties);
		tradingRecorder.setCreateTime(now);
		if (line.length > 3) {
			tradingRecorder.setRemark(String.valueOf(line[3]));
		}

		return tradingRecorder;
	}

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
