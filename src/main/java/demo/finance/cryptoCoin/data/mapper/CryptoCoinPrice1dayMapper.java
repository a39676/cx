package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice1dayMapper {
    long countByExample(CryptoCoinPrice1dayExample example);

    int deleteByExample(CryptoCoinPrice1dayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice1day record);

    int insertSelective(CryptoCoinPrice1day record);

    List<CryptoCoinPrice1day> selectByExampleWithRowbounds(CryptoCoinPrice1dayExample example, RowBounds rowBounds);

    List<CryptoCoinPrice1day> selectByExample(CryptoCoinPrice1dayExample example);

    CryptoCoinPrice1day selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice1day record, @Param("example") CryptoCoinPrice1dayExample example);

    int updateByExample(@Param("record") CryptoCoinPrice1day record, @Param("example") CryptoCoinPrice1dayExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice1day record);

    int updateByPrimaryKey(CryptoCoinPrice1day record);

    CryptoCoinPrice1day findLastDailyData(Long coinType);
}