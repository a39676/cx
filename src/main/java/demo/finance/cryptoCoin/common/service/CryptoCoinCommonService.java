package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPriceMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPriceExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPriceExample.Criteria;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

	@Autowired
	protected CryptoCoinPriceMapper cryptoCoinPriceMapper;

	protected List<CryptoCoinPrice> findCacheDataByTime(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime, LocalDateTime endTime) {
		CryptoCoinPriceExample cacheExample = new CryptoCoinPriceExample();
		Criteria c = cacheExample.createCriteria();
		c.andIsDeleteEqualTo(false).andCoinTypeEqualTo(coinType.getCode()).andCreateTimeGreaterThanOrEqualTo(startTime)
				.andCreateTimeLessThan(endTime);
		if (coinType != null) {
			c.andCurrencyTypeEqualTo(coinType.getCode());
		}

		cacheExample.setOrderByClause("create_time");
		return cryptoCoinPriceMapper.selectByExample(cacheExample);
	}

	protected FilterBODataResult filterData(List<CryptoCoinPriceCommonDataBO> list) {
		FilterBODataResult r = new FilterBODataResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		CryptoCoinPriceCommonDataBO defaultData = list.get(0);
		BigDecimal maxPrice = defaultData.getHighPrice();
		BigDecimal minPrice = defaultData.getLowPrice();
		LocalDateTime maxPriceDateTime = defaultData.getCreateTime();
		LocalDateTime minPriceDateTime = defaultData.getCreateTime();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		for (CryptoCoinPriceCommonDataBO po : list) {
			if (maxPrice.compareTo(po.getHighPrice()) < 0) {
				maxPrice = po.getHighPrice();
				maxPriceDateTime = po.getStartTime();
			} else if (minPrice.compareTo(po.getLowPrice()) > 0) {
				minPrice = po.getLowPrice();
				minPriceDateTime = po.getStartTime();
			}
			
			if(startTime == null || startTime.isAfter(po.getStartTime())) {
				startTime = po.getStartTime();
			}
			if(endTime == null || endTime.isBefore(po.getEndTime())) {
				endTime = po.getEndTime();
			}
			
		}

		r.setMaxPrice(maxPrice);
		r.setMinPrice(minPrice);
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}
	
}
