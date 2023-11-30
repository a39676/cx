package demo.tool.zulip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.tool.zulip.pojo.constant.ZulipUrlConstant;
import demo.tool.zulip.service.ZulipToolService;

@Controller
@RequestMapping(value = ZulipUrlConstant.ROOT)
public class ZulipController {

	@Autowired
	private ZulipToolService zulipToolService;

	@GetMapping(value = ZulipUrlConstant.DELETE_MESSAGE_HISTORY_AUTOMATION)
	@ResponseBody
	public String deleteMessageHistoryAutomation() {
		zulipToolService.deleteMessageHistoryAutomation();
		return "Done";
	}
}
