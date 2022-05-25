package demo.tool.calendarNotice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.calendarNotice.pojo.constant.CalendarNoticeUrl;
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.DeleteCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.EditCalendarNoticeDTO;
import demo.tool.calendarNotice.service.CalendarNoticeService;
import demo.tool.other.pojo.constant.ToolUrlConstant;

@Controller
@RequestMapping(value = ToolUrlConstant.root + CalendarNoticeUrl.ROOT)
public class CalendarNoticeManagerController {

	@Autowired
	private CalendarNoticeService service;

	@GetMapping(value = CalendarNoticeUrl.MANAGER)
	public ModelAndView calendarNoticeManager() {
		return service.getManagerView();
	}

	@PostMapping(value = CalendarNoticeUrl.ADD_NOTICE)
	@ResponseBody
	public CommonResult addCalendarNotice(@RequestBody AddCalendarNoticeDTO dto) {
		return service.addCalendarNotice(dto);
	}

	@PostMapping(value = CalendarNoticeUrl.DELETE_NOTICE)
	@ResponseBody
	public CommonResult deleteNotice(@RequestBody DeleteCalendarNoticeDTO dto) {
		return service.deleteNotice(dto);
	}

	@PostMapping(value = CalendarNoticeUrl.EDIT_NOTICE)
	@ResponseBody
	public CommonResult editNotice(@RequestBody EditCalendarNoticeDTO dto) {
		return service.editNotice(dto);
	}
	
	@PostMapping(value = CalendarNoticeUrl.SEARCH_NOTICE)
	@ResponseBody
	public ModelAndView searchNoticeView() {
		return service.searchNoticeView();
	}

}
