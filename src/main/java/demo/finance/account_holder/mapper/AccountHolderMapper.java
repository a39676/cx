package demo.finance.account_holder.mapper;

import demo.finance.account_holder.pojo.po.AccountHolder;

public interface AccountHolderMapper {
    int insert(AccountHolder record);

    int insertSelective(AccountHolder record);
}