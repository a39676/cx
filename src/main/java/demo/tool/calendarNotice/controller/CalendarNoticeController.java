package demo.tool.calendarNotice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.calendarNotice.pojo.constant.CalendarNoticeUrl;
import demo.tool.calendarNotice.service.CalendarNoticeService;

@Controller
@RequestMapping(value = CalendarNoticeUrl.ROOT)
public class CalendarNoticeController {

	@Autowired
	private CalendarNoticeService service;

	@GetMapping(value = CalendarNoticeUrl.STOP_STRONG_NOTICE)
	@ResponseBody
	public CommonResult stopStrongNotice(@RequestParam("pk") String pk) {
		return service.stopStrongNotic(pk);
	}

}
