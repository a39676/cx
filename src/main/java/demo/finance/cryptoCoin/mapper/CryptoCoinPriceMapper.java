package demo.finance.cryptoCoin.mapper;

import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPriceMapper {
    long countByExample(CryptoCoinPriceExample example);

    int deleteByExample(CryptoCoinPriceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice record);

    int insertSelective(CryptoCoinPrice record);

    List<CryptoCoinPrice> selectByExampleWithRowbounds(CryptoCoinPriceExample example, RowBounds rowBounds);

    List<CryptoCoinPrice> selectByExample(CryptoCoinPriceExample example);

    CryptoCoinPrice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice record, @Param("example") CryptoCoinPriceExample example);

    int updateByExample(@Param("record") CryptoCoinPrice record, @Param("example") CryptoCoinPriceExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice record);

    int updateByPrimaryKey(CryptoCoinPrice record);
}