package demo.finance.trading.mapper;

import demo.finance.trading.pojo.HolderCommonTransation;

public interface HolderCommonTransationMapper {
    int insert(HolderCommonTransation record);

    int insertSelective(HolderCommonTransation record);
}