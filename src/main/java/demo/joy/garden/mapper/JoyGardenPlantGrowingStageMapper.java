package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStage;
import demo.joy.garden.pojo.po.JoyGardenPlantGrowingStageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenPlantGrowingStageMapper {
    long countByExample(JoyGardenPlantGrowingStageExample example);

    int deleteByExample(JoyGardenPlantGrowingStageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyGardenPlantGrowingStage record);

    int insertSelective(JoyGardenPlantGrowingStage record);

    List<JoyGardenPlantGrowingStage> selectByExampleWithRowbounds(JoyGardenPlantGrowingStageExample example, RowBounds rowBounds);

    List<JoyGardenPlantGrowingStage> selectByExample(JoyGardenPlantGrowingStageExample example);

    JoyGardenPlantGrowingStage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyGardenPlantGrowingStage record, @Param("example") JoyGardenPlantGrowingStageExample example);

    int updateByExample(@Param("record") JoyGardenPlantGrowingStage record, @Param("example") JoyGardenPlantGrowingStageExample example);

    int updateByPrimaryKeySelective(JoyGardenPlantGrowingStage record);

    int updateByPrimaryKey(JoyGardenPlantGrowingStage record);
}