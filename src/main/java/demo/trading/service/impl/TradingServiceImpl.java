package demo.trading.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dateTimeHandle.DateUtilCustom;
import demo.account_info.controller.AccountInfoController;
import demo.account_info.pojo.po.AccountInfo;
import demo.baseCommon.pojo.param.controllerParam.InsertNewTransationParam;
import demo.baseCommon.pojo.type.TransationType;
import demo.baseCommon.service.CommonService;
import demo.trading.mapper.HolderCommonTransationCustomMapper;
import demo.trading.mapper.TradingRecorderCustomMapper;
import demo.trading.mapper.TradingRecorderMarkerMapper;
import demo.trading.pojo.CommonTransationParties;
import demo.trading.pojo.po.TradingRecorder;
import demo.trading.pojo.po.TradingRecorderMarker;
import demo.trading.pojo.result.InsertTradingRecorderResult;
import demo.trading.service.TradingService;
import ioHandle.FileUtilCustom;
import numericHandel.NumericUtilCustom;

@Service
public class TradingServiceImpl extends CommonService implements TradingService {

	@Autowired
	private AccountInfoController accountInfoController;

	@Autowired
	private TradingRecorderCustomMapper tradingRecorderCustomMapper;

	@Autowired
	private TradingRecorderMarkerMapper tradingRecorderMarkerMapper;

	@Autowired
	private HolderCommonTransationCustomMapper holderCommonTransationCustomMapper;

	private boolean insertTradingRecorderMarker(TradingRecorder tradingRecorder) {
		TradingRecorderMarker marker = new TradingRecorderMarker();
		marker.setTradingId(tradingRecorder.getTradingId());
		marker.setCreateTime(tradingRecorder.getCreateTime());
		marker.setMarker(tradingRecorder.getInfos());

		if (tradingRecorderMarkerMapper.insert(marker) == 1) {
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

		tradingRecorderCustomMapper.isnertTradingRecorder(tradingRecorder);
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

		tradingRecorderCustomMapper.isnertTradingRecorder(tradingRecorder);
		insertTradingRecorderMarker(tradingRecorder);

		accountInfoController.updateAccountAmount(accountInfo, tradingRecorder.getAmount());

		return tradingRecorder.getTradingId();
	}

	@Override
	public TradingRecorder getTradingRecordById(Long tradingRecorderId) {
		return tradingRecorderCustomMapper.getTradingRecordById(tradingRecorderId);
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

		FileUtilCustom fileHandle = new FileUtilCustom();
		File[] files = mainFolder.listFiles();
//		List<File> fileList = new ArrayList<File>();
		for (File tmpFile : files) {
			if (tmpFile.isFile() && tmpFile.getName().endsWith(".csv")
					&& tmpFile.getName().substring(0, tmpFile.getName().lastIndexOf(".")).matches("\\d{16,19}")) {
				result.append(importTradingRecordFromFile(tmpFile, fileHandle));
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
}
