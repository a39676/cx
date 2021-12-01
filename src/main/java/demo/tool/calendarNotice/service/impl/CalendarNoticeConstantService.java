package demo.tool.calendarNotice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;

@Scope("singleton")
@Service
public class CalendarNoticeConstantService extends CommonService {

	private List<CalendarNotice> strongNoticeList = new ArrayList<>();

	@Override
	public String toString() {
		return "CalendarNoticeConstantService [strongNoticeList=" + strongNoticeList + "]";
	}

	public List<CalendarNotice> getStrongNoticeList() {
		return strongNoticeList;
	}

	public void setStrongNoticeList(List<CalendarNotice> strongNoticeList) {
		this.strongNoticeList = strongNoticeList;
	}

}
