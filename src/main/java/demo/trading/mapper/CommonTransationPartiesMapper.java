package demo.trading.mapper;

import demo.trading.pojo.CommonTransationParties;

public interface CommonTransationPartiesMapper {
    int insert(CommonTransationParties record);

    int insertSelective(CommonTransationParties record);
}