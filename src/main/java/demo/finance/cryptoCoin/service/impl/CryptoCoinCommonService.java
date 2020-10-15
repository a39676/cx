package demo.finance.cryptoCoin.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.type.CurrencyType;
import cryptoCoin.pojo.type.CryptoCoinType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.mapper.CryptoCoinPriceMapper;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample.Criteria;

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
	
}
