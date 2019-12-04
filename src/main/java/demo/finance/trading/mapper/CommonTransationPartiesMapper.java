package demo.finance.trading.mapper;

import demo.finance.trading.pojo.CommonTransationParties;

public interface CommonTransationPartiesMapper {
    int insert(CommonTransationParties record);

    int insertSelective(CommonTransationParties record);
}