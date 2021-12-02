package demo.test.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.tool.calendarNotice.pojo.po.CalendarNotice;
import demo.tool.calendarNotice.service.CalendarNoticeService;
import demo.tool.calendarNotice.service.CalendarNoticeStrongNoticeService;
import demo.tool.calendarNotice.service.impl.CalendarNoticeStrongNoticeServiceImpl;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		TestDTO t = new TestDTO();
		t.setDatetime(LocalDateTime.now());
		return t.toString();
	}

	@GetMapping(value = "/test2")
	@ResponseBody
	public String test2() {
		TestDTO t = new TestDTO();
		t.setDatetime(LocalDateTime.now());
		JSONObject j = JSONObject.fromObject(t);
		t = (TestDTO) JSONObject.toBean(j, TestDTO.class);
		return j.toString();
	}
	
	@Autowired
	private CalendarNoticeService service;

	@GetMapping(value = "/t1")
	@ResponseBody
	public String t1() {
		service.findAndSendNotice();
		return "done";
	}
	
	@GetMapping(value = "/t2")
	@ResponseBody
	public String t2() {
		service.findAndSendStrongNotice();
		return "done";
	}

	@Autowired
	protected CalendarNoticeStrongNoticeServiceImpl cs;
	
	@GetMapping(value = "/t3")
	@ResponseBody
	public List<CalendarNotice> t3() {
		return cs.getStrongNoticeList();
	}
	
	@Autowired
	private CalendarNoticeStrongNoticeService sr;
	
	@GetMapping(value = "/a1")
	@ResponseBody
	public List<CalendarNotice> a3() {
		return sr.getStrongNoticeList();
	}
	
}
