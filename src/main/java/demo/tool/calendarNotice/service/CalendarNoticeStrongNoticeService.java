package demo.tool.calendarNotice.service;

import java.util.List;

import demo.tool.calendarNotice.pojo.bo.StrongNoticeBO;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;

public interface CalendarNoticeStrongNoticeService {

	void addStrongNotice(CalendarNotice strongNotice);

	List<StrongNoticeBO> getStrongNoticeList();

	void stopStrongNotice(Long id);

	StrongNoticeBO getStrongNotice(Long id);

}
