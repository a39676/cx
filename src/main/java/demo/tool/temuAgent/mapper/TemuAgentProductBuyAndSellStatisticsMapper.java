package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellStatistics;
import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductBuyAndSellStatisticsMapper {
    long countByExample(TemuAgentProductBuyAndSellStatisticsExample example);

    int deleteByExample(TemuAgentProductBuyAndSellStatisticsExample example);

    int deleteByPrimaryKey(Long modelId);

    int insert(TemuAgentProductBuyAndSellStatistics row);

    int insertSelective(TemuAgentProductBuyAndSellStatistics row);

    List<TemuAgentProductBuyAndSellStatistics> selectByExampleWithRowbounds(TemuAgentProductBuyAndSellStatisticsExample example, RowBounds rowBounds);

    List<TemuAgentProductBuyAndSellStatistics> selectByExample(TemuAgentProductBuyAndSellStatisticsExample example);

    TemuAgentProductBuyAndSellStatistics selectByPrimaryKey(Long modelId);

    int updateByExampleSelective(@Param("row") TemuAgentProductBuyAndSellStatistics row, @Param("example") TemuAgentProductBuyAndSellStatisticsExample example);

    int updateByExample(@Param("row") TemuAgentProductBuyAndSellStatistics row, @Param("example") TemuAgentProductBuyAndSellStatisticsExample example);

    int updateByPrimaryKeySelective(TemuAgentProductBuyAndSellStatistics row);

    int updateByPrimaryKey(TemuAgentProductBuyAndSellStatistics row);
}