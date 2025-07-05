package demo.tool.wodian.mapper;

import demo.tool.wodian.pojo.po.WodianMerchantsInfo;
import demo.tool.wodian.pojo.po.WodianMerchantsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WodianMerchantsInfoMapper {
    long countByExample(WodianMerchantsInfoExample example);

    int deleteByExample(WodianMerchantsInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WodianMerchantsInfo row);

    int insertSelective(WodianMerchantsInfo row);

    List<WodianMerchantsInfo> selectByExampleWithRowbounds(WodianMerchantsInfoExample example, RowBounds rowBounds);

    List<WodianMerchantsInfo> selectByExample(WodianMerchantsInfoExample example);

    WodianMerchantsInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") WodianMerchantsInfo row, @Param("example") WodianMerchantsInfoExample example);

    int updateByExample(@Param("row") WodianMerchantsInfo row, @Param("example") WodianMerchantsInfoExample example);

    int updateByPrimaryKeySelective(WodianMerchantsInfo row);

    int updateByPrimaryKey(WodianMerchantsInfo row);
}