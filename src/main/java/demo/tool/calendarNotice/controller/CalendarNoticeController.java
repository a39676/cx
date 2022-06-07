package demo.tool.calendarNotice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.calendarNotice.pojo.constant.CalendarNoticeUrl;
import demo.tool.calendarNotice.pojo.dto.StopPreNoticeDTO;
import demo.tool.calendarNotice.pojo.dto.StopStrongNoticeDTO;
import demo.tool.calendarNotice.service.CalendarNoticeService;

@Controller
@RequestMapping(value = CalendarNoticeUrl.ROOT)
public class CalendarNoticeController extends CommonController {

	@Autowired
	private CalendarNoticeService service;

	@GetMapping(value = CalendarNoticeUrl.STOP_STRONG_NOTICE)
	@ResponseBody
	public ModelAndView stopStrongNotice(@RequestParam("pk") String pk) {
		return service.stopStrongNotice(pk);
	}
	
	@PostMapping(value = CalendarNoticeUrl.STOP_STRONG_NOTICE)
	@ResponseBody
	public CommonResult stopStrongNotice(@RequestBody StopStrongNoticeDTO dto) {
		return service.stopStrongNotic(dto);
	}
	
	@GetMapping(value = CalendarNoticeUrl.STOP_PRE_NOTICE)
	@ResponseBody
	public ModelAndView stopPreNotice(@RequestParam("pk") String pk) {
		return service.stopPreNotice(pk);
	}
	
	@PostMapping(value = CalendarNoticeUrl.STOP_PRE_NOTICE)
	@ResponseBody
	public CommonResult stopPreNotic(@RequestBody StopPreNoticeDTO dto) {
		return service.stopPreNotic(dto);
	}

}
