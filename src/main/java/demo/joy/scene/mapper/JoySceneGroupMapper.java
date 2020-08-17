package demo.joy.scene.mapper;

import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.po.JoySceneGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySceneGroupMapper {
    long countByExample(JoySceneGroupExample example);

    int deleteByExample(JoySceneGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoySceneGroup record);

    int insertSelective(JoySceneGroup record);

    List<JoySceneGroup> selectByExampleWithRowbounds(JoySceneGroupExample example, RowBounds rowBounds);

    List<JoySceneGroup> selectByExample(JoySceneGroupExample example);

    JoySceneGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoySceneGroup record, @Param("example") JoySceneGroupExample example);

    int updateByExample(@Param("record") JoySceneGroup record, @Param("example") JoySceneGroupExample example);

    int updateByPrimaryKeySelective(JoySceneGroup record);

    int updateByPrimaryKey(JoySceneGroup record);
}