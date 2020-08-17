package demo.joy.scene.mapper;

import demo.joy.scene.pojo.po.JoySceneGroupRelation;
import demo.joy.scene.pojo.po.JoySceneGroupRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySceneGroupRelationMapper {
    long countByExample(JoySceneGroupRelationExample example);

    int deleteByExample(JoySceneGroupRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoySceneGroupRelation record);

    int insertSelective(JoySceneGroupRelation record);

    List<JoySceneGroupRelation> selectByExampleWithRowbounds(JoySceneGroupRelationExample example, RowBounds rowBounds);

    List<JoySceneGroupRelation> selectByExample(JoySceneGroupRelationExample example);

    JoySceneGroupRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoySceneGroupRelation record, @Param("example") JoySceneGroupRelationExample example);

    int updateByExample(@Param("record") JoySceneGroupRelation record, @Param("example") JoySceneGroupRelationExample example);

    int updateByPrimaryKeySelective(JoySceneGroupRelation record);

    int updateByPrimaryKey(JoySceneGroupRelation record);
}