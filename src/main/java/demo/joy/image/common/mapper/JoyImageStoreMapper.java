package demo.joy.image.common.mapper;

import demo.joy.image.common.pojo.po.JoyImageStore;
import demo.joy.image.common.pojo.po.JoyImageStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyImageStoreMapper {
    long countByExample(JoyImageStoreExample example);

    int deleteByExample(JoyImageStoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyImageStore record);

    int insertSelective(JoyImageStore record);

    List<JoyImageStore> selectByExampleWithRowbounds(JoyImageStoreExample example, RowBounds rowBounds);

    List<JoyImageStore> selectByExample(JoyImageStoreExample example);

    JoyImageStore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyImageStore record, @Param("example") JoyImageStoreExample example);

    int updateByExample(@Param("record") JoyImageStore record, @Param("example") JoyImageStoreExample example);

    int updateByPrimaryKeySelective(JoyImageStore record);

    int updateByPrimaryKey(JoyImageStore record);
}