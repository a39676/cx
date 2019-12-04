package demo.finance.trading.mapper;

import demo.finance.trading.pojo.ConsumptionType;

public interface ConsumptionTypeMapper {
    int insert(ConsumptionType record);

    int insertSelective(ConsumptionType record);
}