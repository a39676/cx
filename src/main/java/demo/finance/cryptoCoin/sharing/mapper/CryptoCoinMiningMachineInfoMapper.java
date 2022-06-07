package demo.finance.cryptoCoin.sharing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineInfo;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineInfoExample;

public interface CryptoCoinMiningMachineInfoMapper {
    long countByExample(CryptoCoinMiningMachineInfoExample example);

    int deleteByExample(CryptoCoinMiningMachineInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinMiningMachineInfo record);

    int insertSelective(CryptoCoinMiningMachineInfo record);

    List<CryptoCoinMiningMachineInfo> selectByExampleWithRowbounds(CryptoCoinMiningMachineInfoExample example, RowBounds rowBounds);

    List<CryptoCoinMiningMachineInfo> selectByExample(CryptoCoinMiningMachineInfoExample example);

    CryptoCoinMiningMachineInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinMiningMachineInfo record, @Param("example") CryptoCoinMiningMachineInfoExample example);

    int updateByExample(@Param("record") CryptoCoinMiningMachineInfo record, @Param("example") CryptoCoinMiningMachineInfoExample example);

    int updateByPrimaryKeySelective(CryptoCoinMiningMachineInfo record);

    int updateByPrimaryKey(CryptoCoinMiningMachineInfo record);
}