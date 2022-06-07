package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenPlant;
import demo.joy.garden.pojo.po.JoyGardenPlantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenPlantMapper {
    long countByExample(JoyGardenPlantExample example);

    int deleteByExample(JoyGardenPlantExample example);

    int deleteByPrimaryKey(Long landId);

    int insert(JoyGardenPlant record);

    int insertSelective(JoyGardenPlant record);

    List<JoyGardenPlant> selectByExampleWithRowbounds(JoyGardenPlantExample example, RowBounds rowBounds);

    List<JoyGardenPlant> selectByExample(JoyGardenPlantExample example);

    JoyGardenPlant selectByPrimaryKey(Long landId);

    int updateByExampleSelective(@Param("record") JoyGardenPlant record, @Param("example") JoyGardenPlantExample example);

    int updateByExample(@Param("record") JoyGardenPlant record, @Param("example") JoyGardenPlantExample example);

    int updateByPrimaryKeySelective(JoyGardenPlant record);

    int updateByPrimaryKey(JoyGardenPlant record);
}