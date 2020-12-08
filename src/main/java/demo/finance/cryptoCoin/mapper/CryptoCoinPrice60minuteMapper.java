package demo.finance.cryptoCoin.mapper;

import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice60minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice60minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice60minuteMapper {
    long countByExample(CryptoCoinPrice60minuteExample example);

    int deleteByExample(CryptoCoinPrice60minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice60minute record);

    int insertSelective(CryptoCoinPrice60minute record);

    List<CryptoCoinPrice60minute> selectByExampleWithRowbounds(CryptoCoinPrice60minuteExample example, RowBounds rowBounds);

    List<CryptoCoinPrice60minute> selectByExample(CryptoCoinPrice60minuteExample example);

    CryptoCoinPrice60minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice60minute record, @Param("example") CryptoCoinPrice60minuteExample example);

    int updateByExample(@Param("record") CryptoCoinPrice60minute record, @Param("example") CryptoCoinPrice60minuteExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice60minute record);

    int updateByPrimaryKey(CryptoCoinPrice60minute record);
}