package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice10minute;
import demo.finance.metal.pojo.po.MetalPrice10minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice10minuteMapper {
    long countByExample(MetalPrice10minuteExample example);

    int deleteByExample(MetalPrice10minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice10minute record);

    int insertSelective(MetalPrice10minute record);

    List<MetalPrice10minute> selectByExampleWithRowbounds(MetalPrice10minuteExample example, RowBounds rowBounds);

    List<MetalPrice10minute> selectByExample(MetalPrice10minuteExample example);

    MetalPrice10minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice10minute record, @Param("example") MetalPrice10minuteExample example);

    int updateByExample(@Param("record") MetalPrice10minute record, @Param("example") MetalPrice10minuteExample example);

    int updateByPrimaryKeySelective(MetalPrice10minute record);

    int updateByPrimaryKey(MetalPrice10minute record);
}