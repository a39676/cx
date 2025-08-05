package demo.tool.temuAgent.mapper;

import demo.tool.temuAgent.pojo.po.TemuAgentProductModel;
import demo.tool.temuAgent.pojo.po.TemuAgentProductModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TemuAgentProductModelMapper {
    long countByExample(TemuAgentProductModelExample example);

    int deleteByExample(TemuAgentProductModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TemuAgentProductModel row);

    int insertSelective(TemuAgentProductModel row);

    List<TemuAgentProductModel> selectByExampleWithRowbounds(TemuAgentProductModelExample example, RowBounds rowBounds);

    List<TemuAgentProductModel> selectByExample(TemuAgentProductModelExample example);

    TemuAgentProductModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") TemuAgentProductModel row, @Param("example") TemuAgentProductModelExample example);

    int updateByExample(@Param("row") TemuAgentProductModel row, @Param("example") TemuAgentProductModelExample example);

    int updateByPrimaryKeySelective(TemuAgentProductModel row);

    int updateByPrimaryKey(TemuAgentProductModel row);
}