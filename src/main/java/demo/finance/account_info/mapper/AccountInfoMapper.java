package demo.finance.account_info.mapper;

import demo.finance.account_info.pojo.po.AccountInfo;

public interface AccountInfoMapper {
    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);
}