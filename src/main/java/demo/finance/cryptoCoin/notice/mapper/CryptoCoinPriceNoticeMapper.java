package demo.finance.cryptoCoin.notice.mapper;

import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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
}