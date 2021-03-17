package demo.finance.metal.mapper;

import demo.finance.metal.pojo.po.MetalPrice1day;
import demo.finance.metal.pojo.po.MetalPrice1dayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetalPrice1dayMapper {
    long countByExample(MetalPrice1dayExample example);

    int deleteByExample(MetalPrice1dayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MetalPrice1day record);

    int insertSelective(MetalPrice1day record);

    List<MetalPrice1day> selectByExampleWithRowbounds(MetalPrice1dayExample example, RowBounds rowBounds);

    List<MetalPrice1day> selectByExample(MetalPrice1dayExample example);

    MetalPrice1day selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MetalPrice1day record, @Param("example") MetalPrice1dayExample example);

    int updateByExample(@Param("record") MetalPrice1day record, @Param("example") MetalPrice1dayExample example);

    int updateByPrimaryKeySelective(MetalPrice1day record);

    int updateByPrimaryKey(MetalPrice1day record);
}