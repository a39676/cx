package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenStorehouse;
import demo.joy.garden.pojo.po.JoyGardenStorehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenStorehouseMapper {
    long countByExample(JoyGardenStorehouseExample example);

    int deleteByExample(JoyGardenStorehouseExample example);

    int insert(JoyGardenStorehouse record);

    int insertSelective(JoyGardenStorehouse record);

    List<JoyGardenStorehouse> selectByExampleWithRowbounds(JoyGardenStorehouseExample example, RowBounds rowBounds);

    List<JoyGardenStorehouse> selectByExample(JoyGardenStorehouseExample example);

    int updateByExampleSelective(@Param("record") JoyGardenStorehouse record, @Param("example") JoyGardenStorehouseExample example);

    int updateByExample(@Param("record") JoyGardenStorehouse record, @Param("example") JoyGardenStorehouseExample example);
}