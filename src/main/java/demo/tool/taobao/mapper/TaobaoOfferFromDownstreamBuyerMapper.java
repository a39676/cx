package demo.tool.taobao.mapper;

import demo.tool.taobao.pojo.po.TaobaoOfferFromDownstreamBuyer;
import demo.tool.taobao.pojo.po.TaobaoOfferFromDownstreamBuyerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TaobaoOfferFromDownstreamBuyerMapper {
	
	long countByExample(TaobaoOfferFromDownstreamBuyerExample example);

    int deleteByExample(TaobaoOfferFromDownstreamBuyerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaobaoOfferFromDownstreamBuyer row);

    int insertSelective(TaobaoOfferFromDownstreamBuyer row);

    List<TaobaoOfferFromDownstreamBuyer> selectByExampleWithRowbounds(TaobaoOfferFromDownstreamBuyerExample example, RowBounds rowBounds);

    List<TaobaoOfferFromDownstreamBuyer> selectByExample(TaobaoOfferFromDownstreamBuyerExample example);

    TaobaoOfferFromDownstreamBuyer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TaobaoOfferFromDownstreamBuyer row, @Param("example") TaobaoOfferFromDownstreamBuyerExample example);

    int updateByExample(@Param("row") TaobaoOfferFromDownstreamBuyer row, @Param("example") TaobaoOfferFromDownstreamBuyerExample example);

    int updateByPrimaryKeySelective(TaobaoOfferFromDownstreamBuyer row);

    int updateByPrimaryKey(TaobaoOfferFromDownstreamBuyer row);
}