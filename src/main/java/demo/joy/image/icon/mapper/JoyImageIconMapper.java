package demo.joy.image.icon.mapper;

import demo.joy.image.icon.pojo.po.JoyImageIcon;
import demo.joy.image.icon.pojo.po.JoyImageIconExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyImageIconMapper {
    long countByExample(JoyImageIconExample example);

    int deleteByExample(JoyImageIconExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyImageIcon record);

    int insertSelective(JoyImageIcon record);

    List<JoyImageIcon> selectByExampleWithRowbounds(JoyImageIconExample example, RowBounds rowBounds);

    List<JoyImageIcon> selectByExample(JoyImageIconExample example);

    JoyImageIcon selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyImageIcon record, @Param("example") JoyImageIconExample example);

    int updateByExample(@Param("record") JoyImageIcon record, @Param("example") JoyImageIconExample example);

    int updateByPrimaryKeySelective(JoyImageIcon record);

    int updateByPrimaryKey(JoyImageIcon record);
}