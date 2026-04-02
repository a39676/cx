package demo.tool.taobao.mapper;

import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplier;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaobaoUpstreamSupplierMapper {
    long countByExample(TaobaoUpstreamSupplierExample example);

    int deleteByExample(TaobaoUpstreamSupplierExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoUpstreamSupplier row);

    int insertSelective(TaobaoUpstreamSupplier row);

    List<TaobaoUpstreamSupplier> selectByExampleWithRowbounds(TaobaoUpstreamSupplierExample example, RowBounds rowBounds);

    List<TaobaoUpstreamSupplier> selectByExample(TaobaoUpstreamSupplierExample example);

    TaobaoUpstreamSupplier selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TaobaoUpstreamSupplier row, @Param("example") TaobaoUpstreamSupplierExample example);

    int updateByExample(@Param("row") TaobaoUpstreamSupplier row, @Param("example") TaobaoUpstreamSupplierExample example);

    int updateByPrimaryKeySelective(TaobaoUpstreamSupplier row);

    int updateByPrimaryKey(TaobaoUpstreamSupplier row);
}