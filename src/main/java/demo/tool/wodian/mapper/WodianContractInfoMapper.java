package demo.tool.wodian.mapper;

import demo.tool.wodian.pojo.po.WodianContractInfo;
import demo.tool.wodian.pojo.po.WodianContractInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WodianContractInfoMapper {
    long countByExample(WodianContractInfoExample example);

    int deleteByExample(WodianContractInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WodianContractInfo row);

    int insertSelective(WodianContractInfo row);

    List<WodianContractInfo> selectByExampleWithRowbounds(WodianContractInfoExample example, RowBounds rowBounds);

    List<WodianContractInfo> selectByExample(WodianContractInfoExample example);

    WodianContractInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WodianContractInfo row, @Param("example") WodianContractInfoExample example);

    int updateByExample(@Param("row") WodianContractInfo row, @Param("example") WodianContractInfoExample example);

    int updateByPrimaryKeySelective(WodianContractInfo row);

    int updateByPrimaryKey(WodianContractInfo row);
}