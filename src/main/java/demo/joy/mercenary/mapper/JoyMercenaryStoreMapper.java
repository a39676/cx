package demo.joy.mercenary.mapper;

import demo.joy.mercenary.pojo.po.JoyMercenaryStore;
import demo.joy.mercenary.pojo.po.JoyMercenaryStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyMercenaryStoreMapper {
    long countByExample(JoyMercenaryStoreExample example);

    int deleteByExample(JoyMercenaryStoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyMercenaryStore record);

    int insertSelective(JoyMercenaryStore record);

    List<JoyMercenaryStore> selectByExampleWithRowbounds(JoyMercenaryStoreExample example, RowBounds rowBounds);

    List<JoyMercenaryStore> selectByExample(JoyMercenaryStoreExample example);

    JoyMercenaryStore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyMercenaryStore record, @Param("example") JoyMercenaryStoreExample example);

    int updateByExample(@Param("record") JoyMercenaryStore record, @Param("example") JoyMercenaryStoreExample example);

    int updateByPrimaryKeySelective(JoyMercenaryStore record);

    int updateByPrimaryKey(JoyMercenaryStore record);
}