package demo.joy.character.mapper;

import demo.joy.character.pojo.po.JoyCharacterDetail;
import demo.joy.character.pojo.po.JoyCharacterDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyCharacterDetailMapper {
    long countByExample(JoyCharacterDetailExample example);

    int deleteByExample(JoyCharacterDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyCharacterDetail record);

    int insertSelective(JoyCharacterDetail record);

    List<JoyCharacterDetail> selectByExampleWithRowbounds(JoyCharacterDetailExample example, RowBounds rowBounds);

    List<JoyCharacterDetail> selectByExample(JoyCharacterDetailExample example);

    JoyCharacterDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyCharacterDetail record, @Param("example") JoyCharacterDetailExample example);

    int updateByExample(@Param("record") JoyCharacterDetail record, @Param("example") JoyCharacterDetailExample example);

    int updateByPrimaryKeySelective(JoyCharacterDetail record);

    int updateByPrimaryKey(JoyCharacterDetail record);
}