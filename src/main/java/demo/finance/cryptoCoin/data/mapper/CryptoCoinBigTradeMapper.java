package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTrade;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinBigTradeMapper {
    long countByExample(CryptoCoinBigTradeExample example);

    int deleteByExample(CryptoCoinBigTradeExample example);

    int insert(CryptoCoinBigTrade row);

    int insertSelective(CryptoCoinBigTrade row);

    List<CryptoCoinBigTrade> selectByExampleWithRowbounds(CryptoCoinBigTradeExample example, RowBounds rowBounds);

    List<CryptoCoinBigTrade> selectByExample(CryptoCoinBigTradeExample example);

    int updateByExampleSelective(@Param("row") CryptoCoinBigTrade row, @Param("example") CryptoCoinBigTradeExample example);

    int updateByExample(@Param("row") CryptoCoinBigTrade row, @Param("example") CryptoCoinBigTradeExample example);
}