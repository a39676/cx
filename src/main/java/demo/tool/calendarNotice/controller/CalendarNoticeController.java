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
import demo.tool.calendarNotice.pojo.dto.AddCalendarNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.DeleteNoticeDTO;
import demo.tool.calendarNotice.service.CalendarNoticeService;
import demo.tool.other.pojo.constant.ToolUrlConstant;

@Controller
@RequestMapping(value = ToolUrlConstant.root + "/canlendarNotice")
public class CalendarNoticeController {

	@Autowired
	private CalendarNoticeService service;

	@GetMapping(value = "/manager")
	public ModelAndView calendarNoticeManager() {
		return service.getManagerView();
	}

	@PostMapping(value = "/addNotice")
	@ResponseBody
	public CommonResult addCalendarNotice(@RequestBody AddCalendarNoticeDTO dto) {
		return service.addCalendarNotice(dto);
	}

	@PostMapping(value = "/deleteNotice")
	@ResponseBody
	public CommonResult deleteNotice(@RequestBody DeleteNoticeDTO dto) {
		return service.deleteNotice(dto);
	}

}
