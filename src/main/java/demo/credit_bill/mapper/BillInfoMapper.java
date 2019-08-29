package demo.credit_bill.mapper;

import java.util.List;

import demo.credit_bill.mapper.query.BillInfoQuery;
import demo.credit_bill.pojo.BillInfo;

public interface BillInfoMapper {
	
	int insert(BillInfo record);

    int insertSelective(BillInfo record);
    
    List<BillInfo> getBillBaseInfoByAccountId(BillInfoQuery query);
    
}
    
