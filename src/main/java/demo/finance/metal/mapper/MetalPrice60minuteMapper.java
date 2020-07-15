package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice60minute;
import demo.finance.metal.pojo.po.MetalPrice60minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice60minuteMapper {
    long countByExample(MetalPrice60minuteExample example);

    int deleteByExample(MetalPrice60minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice60minute record);

    int insertSelective(MetalPrice60minute record);

    List<MetalPrice60minute> selectByExampleWithRowbounds(MetalPrice60minuteExample example, RowBounds rowBounds);

    List<MetalPrice60minute> selectByExample(MetalPrice60minuteExample example);

    MetalPrice60minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice60minute record, @Param("example") MetalPrice60minuteExample example);

    int updateByExample(@Param("record") MetalPrice60minute record, @Param("example") MetalPrice60minuteExample example);

    int updateByPrimaryKeySelective(MetalPrice60minute record);

    int updateByPrimaryKey(MetalPrice60minute record);
}