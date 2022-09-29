package demo.pmemo.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.pmemo.service.UrgeNoticeService;

@Controller
@RequestMapping(value = UrgeNoticeUrl.ROOT)
public class UrgeNoticeController extends CommonController {

	@Autowired
	private UrgeNoticeService service;
	
	@GetMapping(value = UrgeNoticeUrl.SET_UPDATE_MSG_WEBHOOK)
	@ResponseBody
	public String setUpdateMsgWebhook() {
		String secretToken = "test" + LocalTime.now().getSecond();
		service.setUpdateMsgWebhook(secretToken);
		return "done";
	}
	
	@PostMapping(value = UrgeNoticeUrl.RECEIVE_URGE_NOTICE_MSG)
	@ResponseBody
	public String receiveUpdateMsgWebhook(@RequestBody String unknowContent) {
		service.receiveUpdateMsgWebhook(unknowContent);
		return "done";
	}
}
