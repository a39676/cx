package demo.finance.cryptoCoin.mining.mapper;

import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfo;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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