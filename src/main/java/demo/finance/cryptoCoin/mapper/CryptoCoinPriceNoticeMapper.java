package demo.finance.cryptoCoin.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceNoticeExample;

public interface CryptoCoinPriceNoticeMapper {
    long countByExample(CryptoCoinPriceNoticeExample example);

    int deleteByExample(CryptoCoinPriceNoticeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinPriceNotice record);

    int insertSelective(CryptoCoinPriceNotice record);

    List<CryptoCoinPriceNotice> selectByExampleWithRowbounds(CryptoCoinPriceNoticeExample example, RowBounds rowBounds);

    List<CryptoCoinPriceNotice> selectByExample(CryptoCoinPriceNoticeExample example);

    CryptoCoinPriceNotice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CryptoCoinPriceNotice record, @Param("example") CryptoCoinPriceNoticeExample example);

    int updateByExample(@Param("record") CryptoCoinPriceNotice record, @Param("example") CryptoCoinPriceNoticeExample example);

    int updateByPrimaryKeySelective(CryptoCoinPriceNotice record);

    int updateByPrimaryKey(CryptoCoinPriceNotice record);
    
    List<CryptoCoinPriceNotice> selectValidNoticeSetting(LocalDateTime now);
}