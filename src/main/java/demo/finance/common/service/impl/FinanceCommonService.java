package demo.finance.common.service.impl;

import java.time.LocalDateTime;

import demo.common.service.CommonService;

public abstract class FinanceCommonService extends CommonService {

	protected LocalDateTime nextStepTime(LocalDateTime time, int minuteStepLong) {
		int currentMinute = time.getMinute();
		int addMinute = 1;
		while ((currentMinute + addMinute) % minuteStepLong != 0) {
			addMinute += 1;
		}
		return time.plusMinutes(addMinute).withSecond(0).withNano(0);
	}

}
