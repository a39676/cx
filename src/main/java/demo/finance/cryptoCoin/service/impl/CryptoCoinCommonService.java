package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.common.pojo.result.FindMaxMinPriceResult;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.mapper.CryptoCoinPriceMapper;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample.Criteria;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

	@Autowired
	protected CryptoCoinPriceMapper cryptoCoinPriceMapper;
	
	protected List<CryptoCoinPrice> findCacheDataByTime(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime, LocalDateTime endTime) {
		CryptoCoinPriceExample cacheExample = new CryptoCoinPriceExample();
		Criteria c = cacheExample.createCriteria();
		c.andIsDeleteEqualTo(false)
		.andCoinTypeEqualTo(coinType.getCode())
		.andCreateTimeGreaterThanOrEqualTo(startTime)
		.andCreateTimeLessThan(endTime);
		if(coinType != null) {
			c.andCurrencyTypeEqualTo(coinType.getCode());
		}
		
		cacheExample.setOrderByClause("create_time");
		return cryptoCoinPriceMapper.selectByExample(cacheExample);
	}
	
	protected FindMaxMinPriceResult findMaxMinPrice(List<CryptoCoinPrice> list) {
		FindMaxMinPriceResult r = new FindMaxMinPriceResult();
		
		if(list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}
		
		CryptoCoinPrice defaultData = list.get(0);
		BigDecimal maxPrice = defaultData.getPrice();
		BigDecimal minPrice = defaultData.getPrice();
		LocalDateTime maxPriceDateTime = defaultData.getCreateTime();
		LocalDateTime minPriceDateTime = defaultData.getCreateTime();
		for (CryptoCoinPrice po : list) {
			if (maxPrice.compareTo(po.getPrice()) < 0) {
				maxPrice = po.getPrice();
				maxPriceDateTime = po.getCreateTime();
			} else if (minPrice.compareTo(po.getPrice()) > 0) {
				minPrice = po.getPrice();
				minPriceDateTime = po.getCreateTime();
			}
		}
		
		r.setMaxPrice(maxPrice);
		r.setMinPrice(minPrice);
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}
	
	protected List<CryptoCoinPrice> findHistoryDateByLastMinutes(CryptoCoinType coinType,
			CurrencyType currencyType, Integer minutes) {
		CryptoCoinPriceExample example = new CryptoCoinPriceExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.now().minusMinutes(minutes));
		;
		example.setOrderByClause("create_time desc");

		return cryptoCoinPriceMapper.selectByExample(example);
	}
}
