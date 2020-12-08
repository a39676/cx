package demo.finance.cryptoCoin.mapper;

import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1week;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1weekExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice1weekMapper {
    long countByExample(CryptoCoinPrice1weekExample example);

    int deleteByExample(CryptoCoinPrice1weekExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice1week record);

    int insertSelective(CryptoCoinPrice1week record);

    List<CryptoCoinPrice1week> selectByExampleWithRowbounds(CryptoCoinPrice1weekExample example, RowBounds rowBounds);

    List<CryptoCoinPrice1week> selectByExample(CryptoCoinPrice1weekExample example);

    CryptoCoinPrice1week selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice1week record, @Param("example") CryptoCoinPrice1weekExample example);

    int updateByExample(@Param("record") CryptoCoinPrice1week record, @Param("example") CryptoCoinPrice1weekExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice1week record);

    int updateByPrimaryKey(CryptoCoinPrice1week record);
}