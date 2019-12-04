package demo.finance.trading.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.finance.trading.pojo.CommonTransationParties;

public interface HolderCommonTransationCustomMapper {

	public List<CommonTransationParties> getCurrentCommonTransation(@Param("holder")Long holderId, @Param("limit")Integer limit);
	
}
