package demo.joy.scene.mapper;

import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.po.JoySceneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySceneMapper {
    long countByExample(JoySceneExample example);

    int deleteByExample(JoySceneExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyScene record);

    int insertSelective(JoyScene record);

    List<JoyScene> selectByExampleWithRowbounds(JoySceneExample example, RowBounds rowBounds);

    List<JoyScene> selectByExample(JoySceneExample example);

    JoyScene selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyScene record, @Param("example") JoySceneExample example);

    int updateByExample(@Param("record") JoyScene record, @Param("example") JoySceneExample example);

    int updateByPrimaryKeySelective(JoyScene record);

    int updateByPrimaryKey(JoyScene record);
}