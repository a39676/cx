package demo.finance.cryptoCoin.mining.mapper;

import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningOutput;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningOutputExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinMiningOutputMapper {
    long countByExample(CryptoCoinMiningOutputExample example);

    int deleteByExample(CryptoCoinMiningOutputExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinMiningOutput record);

    int insertSelective(CryptoCoinMiningOutput record);

    List<CryptoCoinMiningOutput> selectByExampleWithRowbounds(CryptoCoinMiningOutputExample example, RowBounds rowBounds);

    List<CryptoCoinMiningOutput> selectByExample(CryptoCoinMiningOutputExample example);

    CryptoCoinMiningOutput selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinMiningOutput record, @Param("example") CryptoCoinMiningOutputExample example);

    int updateByExample(@Param("record") CryptoCoinMiningOutput record, @Param("example") CryptoCoinMiningOutputExample example);

    int updateByPrimaryKeySelective(CryptoCoinMiningOutput record);

    int updateByPrimaryKey(CryptoCoinMiningOutput record);
}