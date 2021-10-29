package demo.tool.calendarNotice.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.calendarNotice.mapper.CalendarNoticeMapper;
import demo.tool.calendarNotice.pojo.po.CalendarNoticeExample;
import demo.tool.calendarNotice.service.CalendarNoticeService;
import toolPack.dateTimeHandle.Lunar;

@Service
public class CalendarNoticeServiceImpl extends CommonService implements CalendarNoticeService {

//	TODO

	@Autowired
	private CalendarNoticeMapper mapper;

	public void findNoticeByCalendar() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayStart = localDateTimeHandler.atStartOfDay(now);
		CalendarNoticeExample example = new CalendarNoticeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIsLunarCalendarEqualTo(false).andNextNoticeTimeGreaterThanOrEqualTo(todayStart).andNextNoticeTimeLessThanOrEqualTo(now);
		mapper.selectByExample(example);
	}
	
	public void findNoticeByLunarCalendar() {
		LocalDateTime now = LocalDateTime.now();

		Lunar lunarToday = localDateTimeHandler.toLunar(now);
		LocalDateTime lunarDateTimeNow = LocalDateTime.of(lunarToday.getLunarYear(), lunarToday.getLunarMonth(), lunarToday.getLunarDay(), now.getHour(), now.getMinute(), now.getSecond());
		LocalDateTime lunarDateTimeDayStart = localDateTimeHandler.atStartOfDay(lunarDateTimeNow);
		
		CalendarNoticeExample example = new CalendarNoticeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andIsLunarCalendarEqualTo(true).andNextNoticeTimeGreaterThanOrEqualTo(lunarDateTimeNow).andNextNoticeTimeLessThanOrEqualTo(lunarDateTimeDayStart);
		mapper.selectByExample(example);
	}

}
