package demo.finance.cryptoCoin.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinSymbolLeverage;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinSymbolLeverageExample;

public interface CryptoCoinSymbolLeverageMapper {
	long countByExample(CryptoCoinSymbolLeverageExample example);

	int deleteByExample(CryptoCoinSymbolLeverageExample example);

	int deleteByPrimaryKey(Long id);

	int insert(CryptoCoinSymbolLeverage row);

	int insertSelective(CryptoCoinSymbolLeverage row);

	List<CryptoCoinSymbolLeverage> selectByExampleWithRowbounds(CryptoCoinSymbolLeverageExample example,
			RowBounds rowBounds);

	List<CryptoCoinSymbolLeverage> selectByExample(CryptoCoinSymbolLeverageExample example);

	CryptoCoinSymbolLeverage selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") CryptoCoinSymbolLeverage row,
			@Param("example") CryptoCoinSymbolLeverageExample example);

	int updateByExample(@Param("row") CryptoCoinSymbolLeverage row,
			@Param("example") CryptoCoinSymbolLeverageExample example);

	int updateByPrimaryKeySelective(CryptoCoinSymbolLeverage row);

	int updateByPrimaryKey(CryptoCoinSymbolLeverage row);

	List<CryptoCoinSymbolLeverage> selectLastLeverage();
}