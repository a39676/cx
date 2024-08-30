package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrder;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinBigForceOrderMapper {
    long countByExample(CryptoCoinBigForceOrderExample example);

    int deleteByExample(CryptoCoinBigForceOrderExample example);

    int insert(CryptoCoinBigForceOrder row);

    int insertSelective(CryptoCoinBigForceOrder row);

    List<CryptoCoinBigForceOrder> selectByExampleWithRowbounds(CryptoCoinBigForceOrderExample example, RowBounds rowBounds);

    List<CryptoCoinBigForceOrder> selectByExample(CryptoCoinBigForceOrderExample example);

    int updateByExampleSelective(@Param("row") CryptoCoinBigForceOrder row, @Param("example") CryptoCoinBigForceOrderExample example);

    int updateByExample(@Param("row") CryptoCoinBigForceOrder row, @Param("example") CryptoCoinBigForceOrderExample example);
}