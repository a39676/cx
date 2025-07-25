package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowHistory;
import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductFlowHistoryMapper {
    long countByExample(TemuAgentProductFlowHistoryExample example);

    int deleteByExample(TemuAgentProductFlowHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemuAgentProductFlowHistory row);

    int insertSelective(TemuAgentProductFlowHistory row);

    List<TemuAgentProductFlowHistory> selectByExampleWithRowbounds(TemuAgentProductFlowHistoryExample example, RowBounds rowBounds);

    List<TemuAgentProductFlowHistory> selectByExample(TemuAgentProductFlowHistoryExample example);

    TemuAgentProductFlowHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemuAgentProductFlowHistory row, @Param("example") TemuAgentProductFlowHistoryExample example);

    int updateByExample(@Param("row") TemuAgentProductFlowHistory row, @Param("example") TemuAgentProductFlowHistoryExample example);

    int updateByPrimaryKeySelective(TemuAgentProductFlowHistory row);

    int updateByPrimaryKey(TemuAgentProductFlowHistory row);
}