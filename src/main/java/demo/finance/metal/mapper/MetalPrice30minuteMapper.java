package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice30minute;
import demo.finance.metal.pojo.po.MetalPrice30minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice30minuteMapper {
    long countByExample(MetalPrice30minuteExample example);

    int deleteByExample(MetalPrice30minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice30minute record);

    int insertSelective(MetalPrice30minute record);

    List<MetalPrice30minute> selectByExampleWithRowbounds(MetalPrice30minuteExample example, RowBounds rowBounds);

    List<MetalPrice30minute> selectByExample(MetalPrice30minuteExample example);

    MetalPrice30minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice30minute record, @Param("example") MetalPrice30minuteExample example);

    int updateByExample(@Param("record") MetalPrice30minute record, @Param("example") MetalPrice30minuteExample example);

    int updateByPrimaryKeySelective(MetalPrice30minute record);

    int updateByPrimaryKey(MetalPrice30minute record);
}