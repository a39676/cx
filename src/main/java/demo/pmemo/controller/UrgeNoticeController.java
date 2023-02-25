package demo.pmemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.pmemo.service.UrgeNoticeService;
import demo.tool.telegram.pojo.dto.TelegramUpdateMessageDTO;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = UrgeNoticeUrl.ROOT)
public class UrgeNoticeController extends CommonController {

	@Autowired
	private UrgeNoticeService service;
	
	@PostMapping(value = UrgeNoticeUrl.RECEIVE_URGE_NOTICE_MSG)
	@ResponseBody
	public String receiveUpdateMsgWebhook(HttpServletRequest request, @RequestBody TelegramUpdateMessageDTO unknowContent) {
		service.receiveUpdateMsgWebhook(request, unknowContent);
		return "done";
	}
	
}
