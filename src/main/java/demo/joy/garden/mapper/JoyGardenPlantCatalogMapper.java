package demo.joy.garden.mapper;

import demo.joy.garden.pojo.po.JoyGardenPlantCatalog;
import demo.joy.garden.pojo.po.JoyGardenPlantCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface JoyGardenPlantCatalogMapper {
    long countByExample(JoyGardenPlantCatalogExample example);

    int deleteByExample(JoyGardenPlantCatalogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyGardenPlantCatalog record);

    int insertSelective(JoyGardenPlantCatalog record);

    List<JoyGardenPlantCatalog> selectByExampleWithRowbounds(JoyGardenPlantCatalogExample example, RowBounds rowBounds);

    List<JoyGardenPlantCatalog> selectByExample(JoyGardenPlantCatalogExample example);

    JoyGardenPlantCatalog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyGardenPlantCatalog record, @Param("example") JoyGardenPlantCatalogExample example);

    int updateByExample(@Param("record") JoyGardenPlantCatalog record, @Param("example") JoyGardenPlantCatalogExample example);

    int updateByPrimaryKeySelective(JoyGardenPlantCatalog record);

    int updateByPrimaryKey(JoyGardenPlantCatalog record);
}