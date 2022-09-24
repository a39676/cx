package demo.finance.currencyExchangeRate.notice.mapper;

import demo.finance.currencyExchangeRate.notice.pojo.po.CurrencyExchangeRateNotice;
import demo.finance.currencyExchangeRate.notice.pojo.po.CurrencyExchangeRateNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CurrencyExchangeRateNoticeMapper {
    long countByExample(CurrencyExchangeRateNoticeExample example);

    int deleteByExample(CurrencyExchangeRateNoticeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CurrencyExchangeRateNotice row);

    int insertSelective(CurrencyExchangeRateNotice row);

    List<CurrencyExchangeRateNotice> selectByExampleWithRowbounds(CurrencyExchangeRateNoticeExample example, RowBounds rowBounds);

    List<CurrencyExchangeRateNotice> selectByExample(CurrencyExchangeRateNoticeExample example);

    CurrencyExchangeRateNotice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CurrencyExchangeRateNotice row, @Param("example") CurrencyExchangeRateNoticeExample example);

    int updateByExample(@Param("row") CurrencyExchangeRateNotice row, @Param("example") CurrencyExchangeRateNoticeExample example);

    int updateByPrimaryKeySelective(CurrencyExchangeRateNotice row);

    int updateByPrimaryKey(CurrencyExchangeRateNotice row);
}