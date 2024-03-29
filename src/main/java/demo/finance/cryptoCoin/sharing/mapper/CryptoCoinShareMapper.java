package demo.finance.cryptoCoin.sharing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinShare;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinShareExample;

public interface CryptoCoinShareMapper {
    long countByExample(CryptoCoinShareExample example);

    int deleteByExample(CryptoCoinShareExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinShare record);

    int insertSelective(CryptoCoinShare record);

    List<CryptoCoinShare> selectByExampleWithRowbounds(CryptoCoinShareExample example, RowBounds rowBounds);

    List<CryptoCoinShare> selectByExample(CryptoCoinShareExample example);

    CryptoCoinShare selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinShare record, @Param("example") CryptoCoinShareExample example);

    int updateByExample(@Param("record") CryptoCoinShare record, @Param("example") CryptoCoinShareExample example);

    int updateByPrimaryKeySelective(CryptoCoinShare record);

    int updateByPrimaryKey(CryptoCoinShare record);
}