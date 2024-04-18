package demo.finance.cashFlow.mapper;

import demo.finance.cashFlow.pojo.po.CashFlowRecord;
import demo.finance.cashFlow.pojo.po.CashFlowRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CashFlowRecordMapper {
    long countByExample(CashFlowRecordExample example);

    int deleteByExample(CashFlowRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CashFlowRecord row);

    int insertSelective(CashFlowRecord row);

    List<CashFlowRecord> selectByExampleWithRowbounds(CashFlowRecordExample example, RowBounds rowBounds);

    List<CashFlowRecord> selectByExample(CashFlowRecordExample example);

    CashFlowRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CashFlowRecord row, @Param("example") CashFlowRecordExample example);

    int updateByExample(@Param("row") CashFlowRecord row, @Param("example") CashFlowRecordExample example);

    int updateByPrimaryKeySelective(CashFlowRecord row);

    int updateByPrimaryKey(CashFlowRecord row);
}