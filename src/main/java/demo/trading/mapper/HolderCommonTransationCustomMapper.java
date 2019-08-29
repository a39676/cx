package demo.trading.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.trading.pojo.CommonTransationParties;

public interface HolderCommonTransationCustomMapper {

	public List<CommonTransationParties> getCurrentCommonTransation(@Param("holder")Long holderId, @Param("limit")Integer limit);
	
}
