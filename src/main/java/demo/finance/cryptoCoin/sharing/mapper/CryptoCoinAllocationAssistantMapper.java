package demo.finance.cryptoCoin.sharing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistant;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantExample;

public interface CryptoCoinAllocationAssistantMapper {
    long countByExample(CryptoCoinAllocationAssistantExample example);

    int deleteByExample(CryptoCoinAllocationAssistantExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinAllocationAssistant record);

    int insertSelective(CryptoCoinAllocationAssistant record);

    List<CryptoCoinAllocationAssistant> selectByExampleWithRowbounds(CryptoCoinAllocationAssistantExample example, RowBounds rowBounds);

    List<CryptoCoinAllocationAssistant> selectByExample(CryptoCoinAllocationAssistantExample example);

    CryptoCoinAllocationAssistant selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinAllocationAssistant record, @Param("example") CryptoCoinAllocationAssistantExample example);

    int updateByExample(@Param("record") CryptoCoinAllocationAssistant record, @Param("example") CryptoCoinAllocationAssistantExample example);

    int updateByPrimaryKeySelective(CryptoCoinAllocationAssistant record);

    int updateByPrimaryKey(CryptoCoinAllocationAssistant record);
}