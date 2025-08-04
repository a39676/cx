package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellHistory;
import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductBuyAndSellHistoryMapper {
    long countByExample(TemuAgentProductBuyAndSellHistoryExample example);

    int deleteByExample(TemuAgentProductBuyAndSellHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemuAgentProductBuyAndSellHistory row);

    int insertSelective(TemuAgentProductBuyAndSellHistory row);

    List<TemuAgentProductBuyAndSellHistory> selectByExampleWithRowbounds(TemuAgentProductBuyAndSellHistoryExample example, RowBounds rowBounds);

    List<TemuAgentProductBuyAndSellHistory> selectByExample(TemuAgentProductBuyAndSellHistoryExample example);

    TemuAgentProductBuyAndSellHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemuAgentProductBuyAndSellHistory row, @Param("example") TemuAgentProductBuyAndSellHistoryExample example);

    int updateByExample(@Param("row") TemuAgentProductBuyAndSellHistory row, @Param("example") TemuAgentProductBuyAndSellHistoryExample example);

    int updateByPrimaryKeySelective(TemuAgentProductBuyAndSellHistory row);

    int updateByPrimaryKey(TemuAgentProductBuyAndSellHistory row);
}