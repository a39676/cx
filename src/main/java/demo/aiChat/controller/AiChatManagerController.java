package demo.aiChat.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ai.aiChat.pojo.result.GetAiChatHistoryResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.constant.AiChatManagerUrlConstant;
import demo.aiChat.pojo.dto.AiChatUserEditNicknameDTO;
import demo.aiChat.pojo.dto.GetAiChatUserListDTO;
import demo.aiChat.pojo.result.GetAiChatUserListResult;
import demo.aiChat.service.AiChatService;
import demo.aiChat.service.AiChatUserService;
import demo.aiChat.service.impl.AiChatCacheService;
import demo.common.controller.CommonController;
import demo.common.pojo.dto.BaseDTO;

@Controller
@RequestMapping(value = AiChatManagerUrlConstant.ROOT)
public class AiChatManagerController extends CommonController {

	@Autowired
	private AiChatUserService userService;
	@Autowired
	private AiChatService chatService;
	@Autowired
	private AiChatCacheService aiChatCacheService;
	@Autowired
	private AiChatUserService aiChatUserService;

	@PostMapping(value = AiChatManagerUrlConstant.BLOCK_USER_BY_PK)
	@ResponseBody
	public CommonResult blockUser(@RequestBody BaseDTO dto) {
		return userService.blockUserByPk(dto.getPk());
	}

	@PostMapping(value = AiChatManagerUrlConstant.UNLOCK_USER_BY_PK)
	@ResponseBody
	public CommonResult unlockUserByPk(@RequestBody BaseDTO dto) {
		return userService.unlockUserByPk(dto.getPk());
	}

	@PostMapping(value = AiChatManagerUrlConstant.UNWARNING_USER_BY_PK)
	@ResponseBody
	public CommonResult cleanUserWarningMark(@RequestBody BaseDTO dto) {
		return userService.cleanUserWarningMark(dto.getPk());
	}

	@GetMapping(value = AiChatManagerUrlConstant.CHECK_CHAT_HISTORY_BY_PK)
	@ResponseBody
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(
			@RequestParam(value = "aiChatUserPk") String aiChatUserPk) {
		return chatService.findChatHistoryByAiChatUserIdToFrontEnd(aiChatUserPk);
	}

	@GetMapping(value = AiChatManagerUrlConstant.CLEAR_MEMBERSHIP_CACHE_DATA)
	@ResponseBody
	public CommonResult cleanMembershipCacheData() {
		aiChatCacheService.setMembershipCacheMap(new HashMap<>());
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@PostMapping(value = AiChatManagerUrlConstant.USER_LIST)
	@ResponseBody
	public GetAiChatUserListResult userList(@RequestBody GetAiChatUserListDTO dto) {
		return aiChatUserService.getAiChatUserList(dto);
	}

	@GetMapping(value = AiChatManagerUrlConstant.USER_LIST)
	public ModelAndView userListView() {
		return aiChatUserService.getAiChatUserListView();
	}

	@PostMapping(value = AiChatManagerUrlConstant.EDIT_NICKNAME)
	@ResponseBody
	public CommonResult userList(@RequestBody AiChatUserEditNicknameDTO dto) {
		return aiChatUserService.editNickname(dto);
	}
}
