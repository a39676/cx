package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

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

			if (startTime == null || startTime.isAfter(po.getStartTime())) {
				startTime = po.getStartTime();
			}
			if (endTime == null || endTime.isBefore(po.getEndTime())) {
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

	protected CryptoCoinPriceCommonDataBO mergerData(CryptoCoinPriceCommonDataBO o, CryptoCoinPriceCommonDataBO t) {
		if (o == null || t == null) {
			return o;
		}

		try {
			if (o.getStartTime().isAfter(t.getStartTime())) {
				o.setStartTime(t.getStartTime());
				if (t.getStartPrice() != null) {
					o.setStartPrice(t.getStartPrice());
				}
			}
		} catch (Exception e) {
		}

		try {
			if (o.getEndTime().isBefore(t.getEndTime())) {
				o.setEndTime(t.getEndTime());
				if (t.getEndPrice() != null) {
					o.setEndPrice(t.getEndPrice());
				}
			}
		} catch (Exception e) {
		}

		if (t.getHighPrice() != null) {
			if (o.getHighPrice() == null) {
				o.setHighPrice(t.getHighPrice());
			} else if (o.getHighPrice() != null && o.getHighPrice().doubleValue() < t.getHighPrice().doubleValue()) {
				o.setHighPrice(t.getHighPrice());
			}
		}
		
		if(t.getLowPrice() != null) {
			if(o.getLowPrice() == null) {
				o.setLowPrice(t.getLowPrice());
			} else if(o.getLowPrice() != null && o.getLowPrice().doubleValue() > t.getLowPrice().doubleValue()) {
				o.setLowPrice(t.getLowPrice());
			}
		}
		
		return o;
	}
}
