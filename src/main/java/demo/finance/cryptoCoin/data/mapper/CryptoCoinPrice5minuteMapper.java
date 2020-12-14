package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minuteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice5minuteMapper {
    long countByExample(CryptoCoinPrice5minuteExample example);

    int deleteByExample(CryptoCoinPrice5minuteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice5minute record);

    int insertSelective(CryptoCoinPrice5minute record);

    List<CryptoCoinPrice5minute> selectByExampleWithRowbounds(CryptoCoinPrice5minuteExample example, RowBounds rowBounds);

    List<CryptoCoinPrice5minute> selectByExample(CryptoCoinPrice5minuteExample example);

    CryptoCoinPrice5minute selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice5minute record, @Param("example") CryptoCoinPrice5minuteExample example);

    int updateByExample(@Param("record") CryptoCoinPrice5minute record, @Param("example") CryptoCoinPrice5minuteExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice5minute record);

    int updateByPrimaryKey(CryptoCoinPrice5minute record);
}