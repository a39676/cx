package demo.tool.wodian.mapper;

import demo.tool.wodian.pojo.po.WodianSalesmanInfo;
import demo.tool.wodian.pojo.po.WodianSalesmanInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WodianSalesmanInfoMapper {
    long countByExample(WodianSalesmanInfoExample example);

    int deleteByExample(WodianSalesmanInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WodianSalesmanInfo row);

    int insertSelective(WodianSalesmanInfo row);

    List<WodianSalesmanInfo> selectByExampleWithRowbounds(WodianSalesmanInfoExample example, RowBounds rowBounds);

    List<WodianSalesmanInfo> selectByExample(WodianSalesmanInfoExample example);

    WodianSalesmanInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WodianSalesmanInfo row, @Param("example") WodianSalesmanInfoExample example);

    int updateByExample(@Param("row") WodianSalesmanInfo row, @Param("example") WodianSalesmanInfoExample example);

    int updateByPrimaryKeySelective(WodianSalesmanInfo row);

    int updateByPrimaryKey(WodianSalesmanInfo row);
}