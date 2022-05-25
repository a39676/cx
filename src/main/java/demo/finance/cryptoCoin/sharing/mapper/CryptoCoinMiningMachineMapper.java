package demo.finance.cryptoCoin.sharing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachine;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineExample;

public interface CryptoCoinMiningMachineMapper {
    long countByExample(CryptoCoinMiningMachineExample example);

    int deleteByExample(CryptoCoinMiningMachineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinMiningMachine record);

    int insertSelective(CryptoCoinMiningMachine record);

    List<CryptoCoinMiningMachine> selectByExampleWithRowbounds(CryptoCoinMiningMachineExample example, RowBounds rowBounds);

    List<CryptoCoinMiningMachine> selectByExample(CryptoCoinMiningMachineExample example);

    CryptoCoinMiningMachine selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinMiningMachine record, @Param("example") CryptoCoinMiningMachineExample example);

    int updateByExample(@Param("record") CryptoCoinMiningMachine record, @Param("example") CryptoCoinMiningMachineExample example);

    int updateByPrimaryKeySelective(CryptoCoinMiningMachine record);

    int updateByPrimaryKey(CryptoCoinMiningMachine record);
}