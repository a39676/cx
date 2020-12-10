package demo.finance.common.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.service.CommonService;
import demo.tool.service.MailService;
import demo.tool.service.ValidRegexToolService;

public abstract class FinanceCommonService extends CommonService {
	
	@Autowired
	protected MailService mailService;
	@Autowired
	protected ValidRegexToolService validRegexToolService;

	/**
	 * example: 
	 * time = 14:03:05, minuteStepLong = 5, return 14:05:00
	 * time = 14:13:00, minuteStepLong = 12, return 14:24:00 
	 * @param time
	 * @param minuteStepLong
	 * @return
	 */
	protected LocalDateTime nextStepTimeByMinute(LocalDateTime time, int minuteStepLong) {
		int currentMinute = time.getMinute();
		int addMinute = 1;
		while ((currentMinute + addMinute) % minuteStepLong != 0) {
			addMinute += 1;
		}
		return time.plusMinutes(addMinute).withSecond(0).withNano(0);
	}

}
