package demo.finance.cryptoCoin.mapper;

import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice1minuteMapper {
    long countByExample(CryptoCoinPrice1minuteExample example);

    int deleteByExample(CryptoCoinPrice1minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice1minute record);

    int insertSelective(CryptoCoinPrice1minute record);

    List<CryptoCoinPrice1minute> selectByExampleWithRowbounds(CryptoCoinPrice1minuteExample example, RowBounds rowBounds);

    List<CryptoCoinPrice1minute> selectByExample(CryptoCoinPrice1minuteExample example);

    CryptoCoinPrice1minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice1minute record, @Param("example") CryptoCoinPrice1minuteExample example);

    int updateByExample(@Param("record") CryptoCoinPrice1minute record, @Param("example") CryptoCoinPrice1minuteExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice1minute record);

    int updateByPrimaryKey(CryptoCoinPrice1minute record);
}