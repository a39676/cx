package demo.account_info.mapper;

import demo.account_info.pojo.po.AccountInfoMarker;

public interface AccountInfoMarkerMapper {
    int insert(AccountInfoMarker record);

    int insertSelective(AccountInfoMarker record);
}