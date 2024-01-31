package demo.finance.common.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import demo.tool.mail.service.MailService;
import demo.tool.other.service.ValidRegexToolService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import finance.common.pojo.bo.KLineCommonDataBO;

public abstract class FinanceCommonService extends CommonService {
	
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected MailService mailService;
	@Autowired
	protected TelegramService telegramService;
	@Autowired
	protected ValidRegexToolService validRegexToolService;

	/**
	 * example: 
	 * time = 14:03:05, minuteStepLong = 5, return 14:05:00
	 * time = 14:13:00, minuteStepLong = 12, return 14:24:00 
	 * 
	 * 仅限小范围使用, minuteStepLong 必须要能整除60
	 * 
	 * @param time
	 * @param minuteStepLong
	 * @return
	 */
	protected LocalDateTime nextStepStartTimeByMinute(LocalDateTime time, long minuteStepLong) {
		int currentMinute = time.getMinute();
		if(currentMinute % minuteStepLong == 0) {
			return time.plusMinutes(minuteStepLong).withSecond(0).withNano(0);
		}
		int addMinute = 1;
		while ((currentMinute + addMinute) % minuteStepLong != 0) {
			addMinute += 1;
		}
		return time.plusMinutes(addMinute).withSecond(0).withNano(0);
	}
	
	protected <E extends KLineCommonDataBO> FilterBODataResult filterData(List<E> list) {
		FilterBODataResult r = new FilterBODataResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		double maxPrice = Double.MIN_VALUE;
		double minPrice = Double.MAX_VALUE;
		LocalDateTime maxPriceDateTime = null;
		LocalDateTime minPriceDateTime = null;
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		for (KLineCommonDataBO bo : list) {
			if(bo.getHighPrice() != null && bo.getHighPrice().doubleValue() > maxPrice) {
				maxPrice = bo.getHighPrice().doubleValue();
				maxPriceDateTime = bo.getStartTime();
			}
			
			if(bo.getLowPrice() != null && bo.getLowPrice().doubleValue() < minPrice) {
				minPrice = bo.getLowPrice().doubleValue();
				minPriceDateTime = bo.getStartTime();
			}

			if(bo.getStartTime() != null) {
				if (startTime == null || startTime.isAfter(bo.getStartTime())) {
					startTime = bo.getStartTime();
				}
			}
			if(bo.getEndTime() != null) {
				if (endTime == null || endTime.isBefore(bo.getEndTime())) {
					endTime = bo.getEndTime();
				}
			}

		}

		r.setMaxPrice(new BigDecimal(maxPrice));
		r.setMinPrice(new BigDecimal(minPrice));
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}
}
