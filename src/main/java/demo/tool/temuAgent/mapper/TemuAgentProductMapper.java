package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProduct;
import demo.tool.temuAgent.pojo.po.TemuAgentProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductMapper {
    long countByExample(TemuAgentProductExample example);

    int deleteByExample(TemuAgentProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemuAgentProduct row);

    int insertSelective(TemuAgentProduct row);

    List<TemuAgentProduct> selectByExampleWithRowbounds(TemuAgentProductExample example, RowBounds rowBounds);

    List<TemuAgentProduct> selectByExample(TemuAgentProductExample example);

    TemuAgentProduct selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemuAgentProduct row, @Param("example") TemuAgentProductExample example);

    int updateByExample(@Param("row") TemuAgentProduct row, @Param("example") TemuAgentProductExample example);

    int updateByPrimaryKeySelective(TemuAgentProduct row);

    int updateByPrimaryKey(TemuAgentProduct row);
}