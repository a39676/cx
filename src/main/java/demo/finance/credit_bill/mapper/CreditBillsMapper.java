package demo.finance.credit_bill.mapper;

import demo.finance.credit_bill.pojo.po.CreditBills;

public interface CreditBillsMapper {
    int insert(CreditBills record);

    int insertSelective(CreditBills record);
}