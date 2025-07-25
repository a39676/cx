package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductSellStatistics;
import demo.tool.temuAgent.pojo.po.TemuAgentProductSellStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductSellStatisticsMapper {
    long countByExample(TemuAgentProductSellStatisticsExample example);

    int deleteByExample(TemuAgentProductSellStatisticsExample example);

    int deleteByPrimaryKey(Long modelId);

    int insert(TemuAgentProductSellStatistics row);

    int insertSelective(TemuAgentProductSellStatistics row);

    List<TemuAgentProductSellStatistics> selectByExampleWithRowbounds(TemuAgentProductSellStatisticsExample example, RowBounds rowBounds);

    List<TemuAgentProductSellStatistics> selectByExample(TemuAgentProductSellStatisticsExample example);

    TemuAgentProductSellStatistics selectByPrimaryKey(Long modelId);

    int updateByExampleSelective(@Param("row") TemuAgentProductSellStatistics row, @Param("example") TemuAgentProductSellStatisticsExample example);

    int updateByExample(@Param("row") TemuAgentProductSellStatistics row, @Param("example") TemuAgentProductSellStatisticsExample example);

    int updateByPrimaryKeySelective(TemuAgentProductSellStatistics row);

    int updateByPrimaryKey(TemuAgentProductSellStatistics row);
}