package demo.tool.taobao.mapper;

import demo.tool.taobao.pojo.po.TaobaoRefundOrderFromDownstreamBuyer;
import demo.tool.taobao.pojo.po.TaobaoRefundOrderFromDownstreamBuyerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaobaoRefundOrderFromDownstreamBuyerMapper {
    long countByExample(TaobaoRefundOrderFromDownstreamBuyerExample example);

    int deleteByExample(TaobaoRefundOrderFromDownstreamBuyerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoRefundOrderFromDownstreamBuyer row);

    int insertSelective(TaobaoRefundOrderFromDownstreamBuyer row);

    List<TaobaoRefundOrderFromDownstreamBuyer> selectByExampleWithRowbounds(TaobaoRefundOrderFromDownstreamBuyerExample example, RowBounds rowBounds);

    List<TaobaoRefundOrderFromDownstreamBuyer> selectByExample(TaobaoRefundOrderFromDownstreamBuyerExample example);

    TaobaoRefundOrderFromDownstreamBuyer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TaobaoRefundOrderFromDownstreamBuyer row, @Param("example") TaobaoRefundOrderFromDownstreamBuyerExample example);

    int updateByExample(@Param("row") TaobaoRefundOrderFromDownstreamBuyer row, @Param("example") TaobaoRefundOrderFromDownstreamBuyerExample example);

    int updateByPrimaryKeySelective(TaobaoRefundOrderFromDownstreamBuyer row);

    int updateByPrimaryKey(TaobaoRefundOrderFromDownstreamBuyer row);
}