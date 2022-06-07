package demo.joy.image.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.joy.image.pojo.po.JoyImageStore;
import demo.joy.image.pojo.po.JoyImageStoreExample;

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