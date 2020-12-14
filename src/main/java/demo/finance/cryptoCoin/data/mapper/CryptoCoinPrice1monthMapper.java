package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1month;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1monthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinPrice1monthMapper {
    long countByExample(CryptoCoinPrice1monthExample example);

    int deleteByExample(CryptoCoinPrice1monthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPrice1month record);

    int insertSelective(CryptoCoinPrice1month record);

    List<CryptoCoinPrice1month> selectByExampleWithRowbounds(CryptoCoinPrice1monthExample example, RowBounds rowBounds);

    List<CryptoCoinPrice1month> selectByExample(CryptoCoinPrice1monthExample example);

    CryptoCoinPrice1month selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPrice1month record, @Param("example") CryptoCoinPrice1monthExample example);

    int updateByExample(@Param("record") CryptoCoinPrice1month record, @Param("example") CryptoCoinPrice1monthExample example);

    int updateByPrimaryKeySelective(CryptoCoinPrice1month record);

    int updateByPrimaryKey(CryptoCoinPrice1month record);
}