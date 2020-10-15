package demo.joy.mercenary.mapper;

import demo.joy.mercenary.pojo.po.JoySkillUser;
import demo.joy.mercenary.pojo.po.JoySkillUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoySkillUserMapper {
    long countByExample(JoySkillUserExample example);

    int deleteByExample(JoySkillUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoySkillUser record);

    int insertSelective(JoySkillUser record);

    List<JoySkillUser> selectByExampleWithRowbounds(JoySkillUserExample example, RowBounds rowBounds);

    List<JoySkillUser> selectByExample(JoySkillUserExample example);

    JoySkillUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoySkillUser record, @Param("example") JoySkillUserExample example);

    int updateByExample(@Param("record") JoySkillUser record, @Param("example") JoySkillUserExample example);

    int updateByPrimaryKeySelective(JoySkillUser record);

    int updateByPrimaryKey(JoySkillUser record);
}