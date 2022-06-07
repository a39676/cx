package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenInfo;
import demo.joy.garden.pojo.po.JoyGardenInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenInfoMapper {
    long countByExample(JoyGardenInfoExample example);

    int deleteByExample(JoyGardenInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyGardenInfo record);

    int insertSelective(JoyGardenInfo record);

    List<JoyGardenInfo> selectByExampleWithRowbounds(JoyGardenInfoExample example, RowBounds rowBounds);

    List<JoyGardenInfo> selectByExample(JoyGardenInfoExample example);

    JoyGardenInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyGardenInfo record, @Param("example") JoyGardenInfoExample example);

    int updateByExample(@Param("record") JoyGardenInfo record, @Param("example") JoyGardenInfoExample example);

    int updateByPrimaryKeySelective(JoyGardenInfo record);

    int updateByPrimaryKey(JoyGardenInfo record);
}