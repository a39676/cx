package demo.trading.mapper;

import demo.trading.pojo.ConsumptionType;

public interface ConsumptionTypeMapper {
    int insert(ConsumptionType record);

    int insertSelective(ConsumptionType record);
}