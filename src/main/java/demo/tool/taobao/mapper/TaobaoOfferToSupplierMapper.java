package demo.tool.taobao.mapper;

import demo.tool.taobao.pojo.po.TaobaoOfferToSupplier;
import demo.tool.taobao.pojo.po.TaobaoOfferToSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaobaoOfferToSupplierMapper {
    long countByExample(TaobaoOfferToSupplierExample example);

    int deleteByExample(TaobaoOfferToSupplierExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoOfferToSupplier row);

    int insertSelective(TaobaoOfferToSupplier row);

    List<TaobaoOfferToSupplier> selectByExampleWithRowbounds(TaobaoOfferToSupplierExample example, RowBounds rowBounds);

    List<TaobaoOfferToSupplier> selectByExample(TaobaoOfferToSupplierExample example);

    TaobaoOfferToSupplier selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TaobaoOfferToSupplier row, @Param("example") TaobaoOfferToSupplierExample example);

    int updateByExample(@Param("row") TaobaoOfferToSupplier row, @Param("example") TaobaoOfferToSupplierExample example);

    int updateByPrimaryKeySelective(TaobaoOfferToSupplier row);

    int updateByPrimaryKey(TaobaoOfferToSupplier row);
}