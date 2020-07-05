package demo.finance.metal.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPriceExample;

public interface MetalPriceMapper {
    long countByExample(MetalPriceExample example);

    int deleteByExample(MetalPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice record);

    int insertSelective(MetalPrice record);

    List<MetalPrice> selectByExample(MetalPriceExample example);

    MetalPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice record, @Param("example") MetalPriceExample example);

    int updateByExample(@Param("record") MetalPrice record, @Param("example") MetalPriceExample example);

    int updateByPrimaryKeySelective(MetalPrice record);

    int updateByPrimaryKey(MetalPrice record);
}