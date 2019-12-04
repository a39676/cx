package demo.finance.bank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.finance.bank.pojo.param.controllerParam.FindBankUnionParam;
import demo.finance.bank.pojo.po.BankUnion;
import demo.finance.bank.pojo.po.example.BankUnionExample;

public interface BankUnionMapper {

	List<BankUnion> findBankUnionByCondition(FindBankUnionParam param);
	
	long countByExample(BankUnionExample example);

    int deleteByExample(BankUnionExample example);

    int deleteByPrimaryKey(Integer bankUnionId);

    int insert(BankUnion record);

    int insertSelective(BankUnion record);

    List<BankUnion> selectByExample(BankUnionExample example);

    BankUnion selectByPrimaryKey(Integer bankUnionId);

    int updateByExampleSelective(@Param("record") BankUnion record, @Param("example") BankUnionExample example);

    int updateByExample(@Param("record") BankUnion record, @Param("example") BankUnionExample example);

    int updateByPrimaryKeySelective(BankUnion record);

    int updateByPrimaryKey(BankUnion record);
}