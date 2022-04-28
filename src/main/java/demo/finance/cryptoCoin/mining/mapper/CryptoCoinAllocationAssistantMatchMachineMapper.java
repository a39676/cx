package demo.finance.cryptoCoin.mining.mapper;

import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistantMatchMachine;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistantMatchMachineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinAllocationAssistantMatchMachineMapper {
    long countByExample(CryptoCoinAllocationAssistantMatchMachineExample example);

    int deleteByExample(CryptoCoinAllocationAssistantMatchMachineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinAllocationAssistantMatchMachine record);

    int insertSelective(CryptoCoinAllocationAssistantMatchMachine record);

    List<CryptoCoinAllocationAssistantMatchMachine> selectByExampleWithRowbounds(CryptoCoinAllocationAssistantMatchMachineExample example, RowBounds rowBounds);

    List<CryptoCoinAllocationAssistantMatchMachine> selectByExample(CryptoCoinAllocationAssistantMatchMachineExample example);

    CryptoCoinAllocationAssistantMatchMachine selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinAllocationAssistantMatchMachine record, @Param("example") CryptoCoinAllocationAssistantMatchMachineExample example);

    int updateByExample(@Param("record") CryptoCoinAllocationAssistantMatchMachine record, @Param("example") CryptoCoinAllocationAssistantMatchMachineExample example);

    int updateByPrimaryKeySelective(CryptoCoinAllocationAssistantMatchMachine record);

    int updateByPrimaryKey(CryptoCoinAllocationAssistantMatchMachine record);
}