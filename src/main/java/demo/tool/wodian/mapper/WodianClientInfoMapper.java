package demo.tool.wodian.mapper;

import demo.tool.wodian.pojo.po.WodianClientInfo;
import demo.tool.wodian.pojo.po.WodianClientInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WodianClientInfoMapper {
    long countByExample(WodianClientInfoExample example);

    int deleteByExample(WodianClientInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WodianClientInfo row);

    int insertSelective(WodianClientInfo row);

    List<WodianClientInfo> selectByExampleWithRowbounds(WodianClientInfoExample example, RowBounds rowBounds);

    List<WodianClientInfo> selectByExample(WodianClientInfoExample example);

    WodianClientInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WodianClientInfo row, @Param("example") WodianClientInfoExample example);

    int updateByExample(@Param("row") WodianClientInfo row, @Param("example") WodianClientInfoExample example);

    int updateByPrimaryKeySelective(WodianClientInfo row);

    int updateByPrimaryKey(WodianClientInfo row);
}