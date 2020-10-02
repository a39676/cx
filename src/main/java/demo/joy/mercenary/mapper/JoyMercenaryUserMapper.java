package demo.joy.mercenary.mapper;

import demo.joy.mercenary.pojo.po.JoyMercenaryUser;
import demo.joy.mercenary.pojo.po.JoyMercenaryUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyMercenaryUserMapper {
    long countByExample(JoyMercenaryUserExample example);

    int deleteByExample(JoyMercenaryUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyMercenaryUser record);

    int insertSelective(JoyMercenaryUser record);

    List<JoyMercenaryUser> selectByExampleWithRowbounds(JoyMercenaryUserExample example, RowBounds rowBounds);

    List<JoyMercenaryUser> selectByExample(JoyMercenaryUserExample example);

    JoyMercenaryUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyMercenaryUser record, @Param("example") JoyMercenaryUserExample example);

    int updateByExample(@Param("record") JoyMercenaryUser record, @Param("example") JoyMercenaryUserExample example);

    int updateByPrimaryKeySelective(JoyMercenaryUser record);

    int updateByPrimaryKey(JoyMercenaryUser record);
}