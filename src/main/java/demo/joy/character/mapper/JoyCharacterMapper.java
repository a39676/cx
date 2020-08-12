package demo.joy.character.mapper;

import demo.joy.character.pojo.po.JoyCharacter;
import demo.joy.character.pojo.po.JoyCharacterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyCharacterMapper {
    long countByExample(JoyCharacterExample example);

    int deleteByExample(JoyCharacterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyCharacter record);

    int insertSelective(JoyCharacter record);

    List<JoyCharacter> selectByExampleWithRowbounds(JoyCharacterExample example, RowBounds rowBounds);

    List<JoyCharacter> selectByExample(JoyCharacterExample example);

    JoyCharacter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyCharacter record, @Param("example") JoyCharacterExample example);

    int updateByExample(@Param("record") JoyCharacter record, @Param("example") JoyCharacterExample example);

    int updateByPrimaryKeySelective(JoyCharacter record);

    int updateByPrimaryKey(JoyCharacter record);
}