package demo.joy.scene.mapper;

import demo.joy.scene.pojo.po.JoySceneTransmit;
import demo.joy.scene.pojo.po.JoySceneTransmitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySceneTransmitMapper {
    long countByExample(JoySceneTransmitExample example);

    int deleteByExample(JoySceneTransmitExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoySceneTransmit record);

    int insertSelective(JoySceneTransmit record);

    List<JoySceneTransmit> selectByExampleWithRowbounds(JoySceneTransmitExample example, RowBounds rowBounds);

    List<JoySceneTransmit> selectByExample(JoySceneTransmitExample example);

    JoySceneTransmit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoySceneTransmit record, @Param("example") JoySceneTransmitExample example);

    int updateByExample(@Param("record") JoySceneTransmit record, @Param("example") JoySceneTransmitExample example);

    int updateByPrimaryKeySelective(JoySceneTransmit record);

    int updateByPrimaryKey(JoySceneTransmit record);
}