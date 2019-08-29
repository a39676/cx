package demo.credit_bill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.account_info.pojo.po.AccountInfo;
import demo.credit_bill.mapper.BillInfoMapper;
import demo.credit_bill.mapper.CreditBillsMapper;
import demo.credit_bill.mapper.query.BillInfoQuery;
import demo.credit_bill.pojo.BillInfo;
import demo.credit_bill.pojo.BillInfoCustomDetail;
import demo.credit_bill.pojo.CreditBills;
import demo.credit_bill.service.CreditBillsService;

@Service
public class CreditBillsServiceImpl implements CreditBillsService{

	@Autowired
	CreditBillsMapper creditBillsMapper;
	
	@Autowired
	BillInfoMapper billInfoMapper;
	
	public int insertCreditBill(CreditBills creditBill) {
		
		creditBill.setMarker();
		
		return creditBillsMapper.insert(creditBill);
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
