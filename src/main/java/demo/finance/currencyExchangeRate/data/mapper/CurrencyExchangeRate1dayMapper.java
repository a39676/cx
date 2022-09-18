package demo.finance.currencyExchangeRate.data.mapper;

import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1day;
import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1dayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CurrencyExchangeRate1dayMapper {
    long countByExample(CurrencyExchangeRate1dayExample example);

    int deleteByExample(CurrencyExchangeRate1dayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CurrencyExchangeRate1day row);

    int insertSelective(CurrencyExchangeRate1day row);

    List<CurrencyExchangeRate1day> selectByExampleWithRowbounds(CurrencyExchangeRate1dayExample example, RowBounds rowBounds);

    List<CurrencyExchangeRate1day> selectByExample(CurrencyExchangeRate1dayExample example);

    CurrencyExchangeRate1day selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CurrencyExchangeRate1day row, @Param("example") CurrencyExchangeRate1dayExample example);

    int updateByExample(@Param("row") CurrencyExchangeRate1day row, @Param("example") CurrencyExchangeRate1dayExample example);

    int updateByPrimaryKeySelective(CurrencyExchangeRate1day row);

    int updateByPrimaryKey(CurrencyExchangeRate1day row);
}