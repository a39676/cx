package demo.tool.textMessageForward.telegram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.tool.textMessageForward.telegram.service.TelegramService;

@Controller
@RequestMapping(value = ToolUrlConstant.root + "/telegram")
public class TelegramController {
	
	@Autowired
	private TelegramService telegralService;
	
	@GetMapping(value = "/testing")
	@ResponseBody
	public String testing() {
		telegralService.telegramSendingCheck();
		return "done";
	}
}
