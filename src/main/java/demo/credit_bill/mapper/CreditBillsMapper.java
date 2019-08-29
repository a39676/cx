package demo.credit_bill.mapper;

import demo.credit_bill.pojo.CreditBills;

public interface CreditBillsMapper {
    int insert(CreditBills record);

    int insertSelective(CreditBills record);
}