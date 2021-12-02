package demo.tool.calendarNotice.service;

import java.util.List;

import demo.tool.calendarNotice.pojo.po.CalendarNotice;

public interface CalendarNoticeStrongNoticeService {

	void addStrongNotice(CalendarNotice strongNotice);

	List<CalendarNotice> getStrongNoticeList();

	void stopStrongNotice(Long id);

}
