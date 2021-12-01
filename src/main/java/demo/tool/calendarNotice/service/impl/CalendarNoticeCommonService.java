package demo.tool.calendarNotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.service.CommonService;

public abstract class CalendarNoticeCommonService extends CommonService {

	@Autowired
	protected CalendarNoticeConstantService calendarNoticeConstantService;
	
}
