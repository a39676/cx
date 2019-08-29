package demo.account_info.mapper;

import demo.account_info.pojo.po.AccountInfo;

public interface AccountInfoMapper {
    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);
}