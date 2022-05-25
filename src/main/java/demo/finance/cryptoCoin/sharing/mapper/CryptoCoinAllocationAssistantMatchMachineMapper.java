package demo.finance.cryptoCoin.sharing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantMatchMachine;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantMatchMachineExample;

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