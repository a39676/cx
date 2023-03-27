package demo.aiChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import aiChat.pojo.result.GetAiChatHistoryResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.constant.AiChatUrlConstant;
import demo.aiChat.service.AiChatService;
import demo.aiChat.service.AiChatUserService;
import demo.common.controller.CommonController;

@Controller
@RequestMapping(value = AiChatUrlConstant.ROOT)
public class AiChatManagerController extends CommonController {

	@Autowired
	private AiChatUserService userService;
	@Autowired
	private AiChatService chatService;

	@GetMapping(value = AiChatUrlConstant.BLOCK_USER)
	@ResponseBody
	public CommonResult blockUser(@RequestParam(value = "aiChatUserId") String aiChatUserId) {
		return userService.blockUser(aiChatUserId);
	}

	@GetMapping(value = AiChatUrlConstant.UNLOCK_USER)
	@ResponseBody
	public CommonResult unlockUser(@RequestParam(value = "aiChatUserId") String aiChatUserId) {
		return userService.unlockUser(aiChatUserId);
	}

	@GetMapping(value = AiChatUrlConstant.CHECK_CHAT_HISTORY)
	@ResponseBody
	public GetAiChatHistoryResult unlockUser(@RequestParam(value = "aiChatUserId") Long aiChatUserId) {
		return chatService.findChatHistoryByAiChatUserIdToFrontEnd(aiChatUserId);
	}
}