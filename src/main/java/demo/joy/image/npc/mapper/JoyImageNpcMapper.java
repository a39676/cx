package demo.joy.image.npc.mapper;

import demo.joy.image.npc.pojo.po.JoyImageNpc;
import demo.joy.image.npc.pojo.po.JoyImageNpcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyImageNpcMapper {
    long countByExample(JoyImageNpcExample example);

    int deleteByExample(JoyImageNpcExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyImageNpc record);

    int insertSelective(JoyImageNpc record);

    List<JoyImageNpc> selectByExampleWithRowbounds(JoyImageNpcExample example, RowBounds rowBounds);

    List<JoyImageNpc> selectByExample(JoyImageNpcExample example);

    JoyImageNpc selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyImageNpc record, @Param("example") JoyImageNpcExample example);

    int updateByExample(@Param("record") JoyImageNpc record, @Param("example") JoyImageNpcExample example);

    int updateByPrimaryKeySelective(JoyImageNpc record);

    int updateByPrimaryKey(JoyImageNpc record);
}