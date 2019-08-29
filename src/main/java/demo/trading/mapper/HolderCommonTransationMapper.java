package demo.trading.mapper;

import demo.trading.pojo.HolderCommonTransation;

public interface HolderCommonTransationMapper {
    int insert(HolderCommonTransation record);

    int insertSelective(HolderCommonTransation record);
}