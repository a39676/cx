package demo.finance.trading.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.common.pojo.type.TransationType;
import demo.common.service.CommonService;
import demo.config.costom_component.EncryptUtil;
import demo.finance.account_info.pojo.dto.controllerDTO.InsertNewTransationDTO;
import demo.finance.trading.mapper.TradingRecorderMapper;
import demo.finance.trading.mapper.TradingRecorderMarkerMapper;
import demo.finance.trading.pojo.po.TradingRecorder;
import demo.finance.trading.pojo.po.TradingRecorderMarker;
import demo.finance.trading.pojo.result.InsertTradingRecorderResult;
import demo.finance.trading.service.TradingInsertService;
import toolPack.numericHandel.NumericUtilCustom;

@Service
public class TradingInsertServiceImpl extends CommonService implements TradingInsertService {

	@Autowired
	private TradingRecorderMapper tradingMapper;
	@Autowired
	private TradingRecorderMarkerMapper tradingMarkerMapper;
	@Autowired
	private NumericUtilCustom numberUtil;
	@Autowired
	private EncryptUtil encryptUtil;

	private boolean insertTradingRecorderMarker(TradingRecorder tradingRecorder) {
		TradingRecorderMarker marker = new TradingRecorderMarker();
		marker.setTradingId(tradingRecorder.getTradingId());
		marker.setCreateTime(tradingRecorder.getCreateTime());
		marker.setMarker(getTradingRecorderMarker(tradingRecorder));

		if (tradingMarkerMapper.insert(marker) == 1) {
			return true;
		}
		return false;
	}
	
	private String getTradingRecorderMarker(TradingRecorder po) {
		return encryptUtil.Sha1(encryptUtil.ToMd5String(getTradingRecorderInfos(po)));
	}
	
	private String getTradingRecorderInfos(TradingRecorder po) {
		return "" + po.getTradingId() + po.getAccountId() 
				+ po.getAccountNumber() + po.getAmount() + po.getTransationDate() 
				+ po.getTransationParties() + po.getTransationAccountId() + po.getCreateTime() + po.getRemark();
	}

	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public InsertTradingRecorderResult insertTradingRecorder(InsertNewTransationDTO p, Long accountId) {
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
	public TradingRecorder getTradingRecordById(Long tradingRecorderId) {
		return tradingMapper.getTradingRecordById(tradingRecorderId);
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
	 * if(!numberUtil.matchPositiveInteger(accountNumber)) { return null; }
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

	private TradingRecorder buildTradingRecorderFrom(Long newTradingRecordId, InsertNewTransationDTO p,
			Long accountId) {

		if (p.getTransationAmount() == null) {
			return null;
		}

		Date now = new Date();
		Date transationDate = dateHandler.stringToDateUnkonwFormat(p.getTransationDate());
		String transationParties = p.getTransationParties();
		BigDecimal transationAmount = null;
		String accountNumber = p.getAccountNumber();
		if (!numberUtil.matchPositiveInteger(accountNumber)) {
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

	

}
