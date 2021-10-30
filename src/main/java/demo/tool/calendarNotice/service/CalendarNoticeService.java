package demo.tool.calendarNotice.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;

public interface CalendarNoticeService {

	CommonResult addCalendarNotice(AddCalendarNoticeDTO dto);

	void findAndSendNotice();

}
