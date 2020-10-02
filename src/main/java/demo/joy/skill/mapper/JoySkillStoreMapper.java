package demo.joy.skill.mapper;

import demo.joy.skill.pojo.po.JoySkillStore;
import demo.joy.skill.pojo.po.JoySkillStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySkillStoreMapper {
    long countByExample(JoySkillStoreExample example);

    int deleteByExample(JoySkillStoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoySkillStore record);

    int insertSelective(JoySkillStore record);

    List<JoySkillStore> selectByExampleWithRowbounds(JoySkillStoreExample example, RowBounds rowBounds);

    List<JoySkillStore> selectByExample(JoySkillStoreExample example);

    JoySkillStore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoySkillStore record, @Param("example") JoySkillStoreExample example);

    int updateByExample(@Param("record") JoySkillStore record, @Param("example") JoySkillStoreExample example);

    int updateByPrimaryKeySelective(JoySkillStore record);

    int updateByPrimaryKey(JoySkillStore record);
}