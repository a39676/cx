package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductSellHistory;
import demo.tool.temuAgent.pojo.po.TemuAgentProductSellHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductSellHistoryMapper {
    long countByExample(TemuAgentProductSellHistoryExample example);

    int deleteByExample(TemuAgentProductSellHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemuAgentProductSellHistory row);

    int insertSelective(TemuAgentProductSellHistory row);

    List<TemuAgentProductSellHistory> selectByExampleWithRowbounds(TemuAgentProductSellHistoryExample example, RowBounds rowBounds);

    List<TemuAgentProductSellHistory> selectByExample(TemuAgentProductSellHistoryExample example);

    TemuAgentProductSellHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemuAgentProductSellHistory row, @Param("example") TemuAgentProductSellHistoryExample example);

    int updateByExample(@Param("row") TemuAgentProductSellHistory row, @Param("example") TemuAgentProductSellHistoryExample example);

    int updateByPrimaryKeySelective(TemuAgentProductSellHistory row);

    int updateByPrimaryKey(TemuAgentProductSellHistory row);
}