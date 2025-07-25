package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowStatistics;
import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductFlowStatisticsMapper {
    long countByExample(TemuAgentProductFlowStatisticsExample example);

    int deleteByExample(TemuAgentProductFlowStatisticsExample example);

    int deleteByPrimaryKey(Long modelId);

    int insert(TemuAgentProductFlowStatistics row);

    int insertSelective(TemuAgentProductFlowStatistics row);

    List<TemuAgentProductFlowStatistics> selectByExampleWithRowbounds(TemuAgentProductFlowStatisticsExample example, RowBounds rowBounds);

    List<TemuAgentProductFlowStatistics> selectByExample(TemuAgentProductFlowStatisticsExample example);

    TemuAgentProductFlowStatistics selectByPrimaryKey(Long modelId);

    int updateByExampleSelective(@Param("row") TemuAgentProductFlowStatistics row, @Param("example") TemuAgentProductFlowStatisticsExample example);

    int updateByExample(@Param("row") TemuAgentProductFlowStatistics row, @Param("example") TemuAgentProductFlowStatisticsExample example);

    int updateByPrimaryKeySelective(TemuAgentProductFlowStatistics row);

    int updateByPrimaryKey(TemuAgentProductFlowStatistics row);
}