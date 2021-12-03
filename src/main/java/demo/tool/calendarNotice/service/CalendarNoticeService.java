package demo.tool.calendarNotice.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.DeleteCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.EditCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.StopStrongNoticeDTO;

public interface CalendarNoticeService {

	/**
	 * 请输入的公历日期
	 * 如需输入农历类的通知提醒, 需找到与之对应的公历日期
	 * 例: 公历 2021-10-30 对应 农历九月二十五, 若需要在每个九月二十五发出提醒, 则输入 2021-10-30
	 * @param dto
	 * @return
	 */
	CommonResult addCalendarNotice(AddCalendarNoticeDTO dto);

	void findAndSendNotice();

	ModelAndView getManagerView();

	CommonResult deleteNotice(DeleteCalendarNoticeDTO dto);

	CommonResult editNotice(EditCalendarNoticeDTO dto);

	ModelAndView searchNoticeView();

	void findAndSendStrongNotice();

	CommonResult stopStrongNotic(StopStrongNoticeDTO dto);

	ModelAndView stopStrongNotice(String pk);

}
