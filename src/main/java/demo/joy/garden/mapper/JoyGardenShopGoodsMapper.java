package demo.joy.garden.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.joy.garden.pojo.dto.FindShopGoodsPageDTO;
import demo.joy.garden.pojo.po.JoyGardenShopGoods;
import demo.joy.garden.pojo.po.JoyGardenShopGoodsExample;

public interface JoyGardenShopGoodsMapper {
    long countByExample(JoyGardenShopGoodsExample example);

    int deleteByExample(JoyGardenShopGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JoyGardenShopGoods record);

    int insertSelective(JoyGardenShopGoods record);

    List<JoyGardenShopGoods> selectByExampleWithRowbounds(JoyGardenShopGoodsExample example, RowBounds rowBounds);

    List<JoyGardenShopGoods> selectByExample(JoyGardenShopGoodsExample example);

    JoyGardenShopGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JoyGardenShopGoods record, @Param("example") JoyGardenShopGoodsExample example);

    int updateByExample(@Param("record") JoyGardenShopGoods record, @Param("example") JoyGardenShopGoodsExample example);

    int updateByPrimaryKeySelective(JoyGardenShopGoods record);

    int updateByPrimaryKey(JoyGardenShopGoods record);
    
    List<JoyGardenShopGoods> findShopGoodsPage(FindShopGoodsPageDTO dto);
}