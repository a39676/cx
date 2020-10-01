package demo.joy.mercenary.mapper;

import demo.joy.mercenary.pojo.po.JoyUserMercenary;
import demo.joy.mercenary.pojo.po.JoyUserMercenaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyUserMercenaryMapper {
    long countByExample(JoyUserMercenaryExample example);

    int deleteByExample(JoyUserMercenaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyUserMercenary record);

    int insertSelective(JoyUserMercenary record);

    List<JoyUserMercenary> selectByExampleWithRowbounds(JoyUserMercenaryExample example, RowBounds rowBounds);

    List<JoyUserMercenary> selectByExample(JoyUserMercenaryExample example);

    JoyUserMercenary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyUserMercenary record, @Param("example") JoyUserMercenaryExample example);

    int updateByExample(@Param("record") JoyUserMercenary record, @Param("example") JoyUserMercenaryExample example);

    int updateByPrimaryKeySelective(JoyUserMercenary record);

    int updateByPrimaryKey(JoyUserMercenary record);
}