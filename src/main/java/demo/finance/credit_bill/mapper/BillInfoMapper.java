package demo.finance.credit_bill.mapper;

import java.util.List;

import demo.finance.credit_bill.mapper.query.BillInfoQuery;
import demo.finance.credit_bill.pojo.po.BillInfo;

public interface BillInfoMapper {
	
	int insert(BillInfo record);

    int insertSelective(BillInfo record);
    
    List<BillInfo> getBillBaseInfoByAccountId(BillInfoQuery query);
    
}
    
