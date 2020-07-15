package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice1month;
import demo.finance.metal.pojo.po.MetalPrice1monthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice1monthMapper {
    long countByExample(MetalPrice1monthExample example);

    int deleteByExample(MetalPrice1monthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice1month record);

    int insertSelective(MetalPrice1month record);

    List<MetalPrice1month> selectByExampleWithRowbounds(MetalPrice1monthExample example, RowBounds rowBounds);

    List<MetalPrice1month> selectByExample(MetalPrice1monthExample example);

    MetalPrice1month selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice1month record, @Param("example") MetalPrice1monthExample example);

    int updateByExample(@Param("record") MetalPrice1month record, @Param("example") MetalPrice1monthExample example);

    int updateByPrimaryKeySelective(MetalPrice1month record);

    int updateByPrimaryKey(MetalPrice1month record);
}