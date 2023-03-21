package demo.finance.credit_bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.credit_bill.mapper.BillInfoMapper;
import demo.finance.credit_bill.mapper.CreditBillsMapper;
import demo.finance.credit_bill.mapper.query.BillInfoQuery;
import demo.finance.credit_bill.pojo.BillInfoCustomDetail;
import demo.finance.credit_bill.pojo.po.BillInfo;
import demo.finance.credit_bill.pojo.po.CreditBills;
import demo.finance.credit_bill.service.CreditBillsService;
import toolPack.encryptHandle.EncryptUtil;

@Service
public class CreditBillsServiceImpl extends CommonService implements CreditBillsService{

	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	private CreditBillsMapper creditBillsMapper;
	
	@Autowired
	private BillInfoMapper billInfoMapper;
	
	
	
	private String getCreditBillInfos(CreditBills po) {
		return "" + po.getBillId() + po.getAccountId() + po.getLastRefundDate() + po.getBillAmount() + po.getMinRefundAmount()
				+ po.getCreateTime() + po.getRemark();
	}
	
	public int insertCreditBill(CreditBills creditBill) {
		String marker = encryptUtil.sha1(encryptUtil.toMd5String(getCreditBillInfos(creditBill)));
		creditBill.setMarker(marker);
		
		return creditBillsMapper.insert(creditBill);
	}
	
	public boolean checkRemark(CreditBills creditBill) {

		if (StringUtils.isBlank(creditBill.getMarker())) {
			return false;
		}

		return creditBill.getMarker()
				.equals(encryptUtil.sha1(encryptUtil.toMd5String(getCreditBillInfos(creditBill))));
	}
	
	@Override
	public List<BillInfo> getBillBaseInfoByAccountInfos(List<AccountInfo> accountInfos) {
		if(accountInfos == null || accountInfos.size() <= 0) {
			return new ArrayList<BillInfo>();
		}
		
		List<Long> accountInfoIds = new ArrayList<Long>();
		
		for(AccountInfo account : accountInfos) {
			accountInfoIds.add(account.getAccountId());
		}
		
		BillInfoQuery query = new BillInfoQuery();
		query.setAccountIds(accountInfoIds);
		
		return billInfoMapper.getBillBaseInfoByAccountId(query);
	}
	
	@Override
	public List<BillInfo> getBillBaseInfoByAccountInfoIds(List<Long> accountIds) {
		if(accountIds == null || accountIds.size() <= 0) {
			return new ArrayList<BillInfo>();
		}
		
		BillInfoQuery query = new BillInfoQuery();
		query.setAccountIds(accountIds);
		
		return billInfoMapper.getBillBaseInfoByAccountId(query);
	}
	
	@Override
	public List<BillInfo> getBillBaseInfoByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos) {
		if(accountInfos == null || accountInfos.size() <= 0) {
			return new ArrayList<BillInfo>();
		}
		
		List<Long> accountIdList = new ArrayList<Long>();
		
		for(AccountInfoWithBankInfo account : accountInfos) {
			accountIdList.add(account.getAccountId());
		}
		
		BillInfoQuery query = new BillInfoQuery();
		query.setAccountIds(accountIdList);
		
		return billInfoMapper.getBillBaseInfoByAccountId(query);
	}
	
	@Override
	public List<BillInfoCustomDetail> getBillCustomDetailByAccountInfoWithBankInfo(List<AccountInfoWithBankInfo> accountInfos) {
		/*
		 * FIXME 
		 * BillInfoCustomDetail --> vo
		 * account id CAN NOT return to front end
		 */
		if(accountInfos == null || accountInfos.size() <= 0) {
			return new ArrayList<BillInfoCustomDetail>();
		}
		
		List<Long> accountIdList = new ArrayList<Long>();
		
		for(AccountInfoWithBankInfo account : accountInfos) {
			accountIdList.add(account.getAccountId());
		}
		
		BillInfoQuery query = new BillInfoQuery();
		query.setAccountIds(accountIdList);
		
		List<BillInfo> billInfos = billInfoMapper.getBillBaseInfoByAccountId(query);
		
		List<BillInfoCustomDetail> billDetails = new ArrayList<BillInfoCustomDetail>();
		for(BillInfo billInfo : billInfos) {
			billDetails.add(new BillInfoCustomDetail(billInfo));
		}
		
		return billDetails;
	}
	
}
