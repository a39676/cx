package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenLands;
import demo.joy.garden.pojo.po.JoyGardenLandsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenLandsMapper {
    long countByExample(JoyGardenLandsExample example);

    int deleteByExample(JoyGardenLandsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyGardenLands record);

    int insertSelective(JoyGardenLands record);

    List<JoyGardenLands> selectByExampleWithRowbounds(JoyGardenLandsExample example, RowBounds rowBounds);

    List<JoyGardenLands> selectByExample(JoyGardenLandsExample example);

    JoyGardenLands selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyGardenLands record, @Param("example") JoyGardenLandsExample example);

    int updateByExample(@Param("record") JoyGardenLands record, @Param("example") JoyGardenLandsExample example);

    int updateByPrimaryKeySelective(JoyGardenLands record);

    int updateByPrimaryKey(JoyGardenLands record);
}