package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMove;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinBigMoveMapper {
    long countByExample(CryptoCoinBigMoveExample example);

    int deleteByExample(CryptoCoinBigMoveExample example);

    int insert(CryptoCoinBigMove row);

    int insertSelective(CryptoCoinBigMove row);

    List<CryptoCoinBigMove> selectByExampleWithRowbounds(CryptoCoinBigMoveExample example, RowBounds rowBounds);

    List<CryptoCoinBigMove> selectByExample(CryptoCoinBigMoveExample example);

    int updateByExampleSelective(@Param("row") CryptoCoinBigMove row, @Param("example") CryptoCoinBigMoveExample example);

    int updateByExample(@Param("row") CryptoCoinBigMove row, @Param("example") CryptoCoinBigMoveExample example);
}