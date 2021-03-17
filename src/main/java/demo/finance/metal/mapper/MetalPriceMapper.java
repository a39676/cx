package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPriceMapper {
    long countByExample(MetalPriceExample example);

    int deleteByExample(MetalPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice record);

    int insertSelective(MetalPrice record);

    List<MetalPrice> selectByExampleWithRowbounds(MetalPriceExample example, RowBounds rowBounds);

    List<MetalPrice> selectByExample(MetalPriceExample example);

    MetalPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice record, @Param("example") MetalPriceExample example);

    int updateByExample(@Param("record") MetalPrice record, @Param("example") MetalPriceExample example);

    int updateByPrimaryKeySelective(MetalPrice record);

    int updateByPrimaryKey(MetalPrice record);
}