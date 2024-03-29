package demo.finance.cryptoCoin.notice.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;

public interface CryptoCoinPriceNoticeMapper {
	long countByExample(CryptoCoinPriceNoticeExample example);

	int deleteByExample(CryptoCoinPriceNoticeExample example);

	int deleteByPrimaryKey(Long id);

	int insert(CryptoCoinPriceNotice row);

	int insertSelective(CryptoCoinPriceNotice row);

	List<CryptoCoinPriceNotice> selectByExampleWithRowbounds(CryptoCoinPriceNoticeExample example, RowBounds rowBounds);

	List<CryptoCoinPriceNotice> selectByExample(CryptoCoinPriceNoticeExample example);

	CryptoCoinPriceNotice selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") CryptoCoinPriceNotice row,
			@Param("example") CryptoCoinPriceNoticeExample example);

	int updateByExample(@Param("row") CryptoCoinPriceNotice row,
			@Param("example") CryptoCoinPriceNoticeExample example);

	int updateByPrimaryKeySelective(CryptoCoinPriceNotice row);

	int updateByPrimaryKey(CryptoCoinPriceNotice row);

	List<CryptoCoinPriceNotice> selectValidNoticeSetting(LocalDateTime now);
}