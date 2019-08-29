package demo.account_holder.mapper;

import demo.account_holder.pojo.po.AccountHolder;

public interface AccountHolderMapper {
    int insert(AccountHolder record);

    int insertSelective(AccountHolder record);
}