package demo.pmemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.pmemo.pojo.constant.UrgeNoticeManagerUrl;
import demo.pmemo.service.UrgeNoticeManagerService;

@Controller
@RequestMapping(value = UrgeNoticeManagerUrl.ROOT)
public class UrgeNoticeManagerController extends CommonController {

	@Autowired
	private UrgeNoticeManagerService service;
	
	@GetMapping(value = UrgeNoticeManagerUrl.SET_UPDATE_MSG_WEBHOOK)
	@ResponseBody
	public String setUpdateMsgWebhook() {
		service.setUpdateMsgWebhook();
		return "done";
	}
	
}
