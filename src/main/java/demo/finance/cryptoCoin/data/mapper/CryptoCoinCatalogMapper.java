package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinCatalogMapper {
    long countByExample(CryptoCoinCatalogExample example);

    int deleteByExample(CryptoCoinCatalogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinCatalog record);

    int insertSelective(CryptoCoinCatalog record);

    List<CryptoCoinCatalog> selectByExampleWithRowbounds(CryptoCoinCatalogExample example, RowBounds rowBounds);

    List<CryptoCoinCatalog> selectByExample(CryptoCoinCatalogExample example);

    CryptoCoinCatalog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinCatalog record, @Param("example") CryptoCoinCatalogExample example);

    int updateByExample(@Param("record") CryptoCoinCatalog record, @Param("example") CryptoCoinCatalogExample example);

    int updateByPrimaryKeySelective(CryptoCoinCatalog record);

    int updateByPrimaryKey(CryptoCoinCatalog record);
    
    CryptoCoinCatalog findLastCatalog();
}