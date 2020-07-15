package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice5minute;
import demo.finance.metal.pojo.po.MetalPrice5minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice5minuteMapper {
    long countByExample(MetalPrice5minuteExample example);

    int deleteByExample(MetalPrice5minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice5minute record);

    int insertSelective(MetalPrice5minute record);

    List<MetalPrice5minute> selectByExampleWithRowbounds(MetalPrice5minuteExample example, RowBounds rowBounds);

    List<MetalPrice5minute> selectByExample(MetalPrice5minuteExample example);

    MetalPrice5minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice5minute record, @Param("example") MetalPrice5minuteExample example);

    int updateByExample(@Param("record") MetalPrice5minute record, @Param("example") MetalPrice5minuteExample example);

    int updateByPrimaryKeySelective(MetalPrice5minute record);

    int updateByPrimaryKey(MetalPrice5minute record);
}