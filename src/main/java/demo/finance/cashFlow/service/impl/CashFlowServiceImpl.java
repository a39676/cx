package demo.finance.cashFlow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.cashFlow.mapper.CashFlowRecordMapper;
import demo.finance.cashFlow.pojo.po.CashFlowRecord;
import demo.finance.cashFlow.pojo.po.CashFlowRecordExample;
import demo.finance.cashFlow.pojo.result.CashFlowSummaryResult;
import demo.finance.cashFlow.service.CashFlowService;

@Service
public class CashFlowServiceImpl extends CommonService implements CashFlowService {

	@Autowired
	private CashFlowRecordMapper cashFlowRecordMapper;

	@Override
	public CashFlowSummaryResult getSummary() {
		CashFlowSummaryResult r = new CashFlowSummaryResult();

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			return r;
		}

		CashFlowRecordExample example = new CashFlowRecordExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(false);
		List<CashFlowRecord> recordList = cashFlowRecordMapper.selectByExample(example);
		
		
//		TODO

		r.setIsSuccess();
		return r;
	}
	
	/* 
	 * TODO 各种消费按时间单位转换
	 * 1. day to year ---> * 365.25
	 * 2. week to year ---> * 52
	 * 3. day to month ---> * 30.4
	 * 4. week to month ---> * 4.5 
	 */
	
	
}
