package demo.ai.manager.controller;

import java.math.BigDecimal;
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
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.service.AiChatService;
import demo.ai.aiChat.service.AiUserService;
import demo.ai.aiChat.service.impl.AiChatCacheService;
import demo.ai.manager.pojo.constant.AiManagerUrlConstant;
import demo.ai.manager.pojo.dto.AiChatUserEditNicknameDTO;
import demo.ai.manager.pojo.dto.GetAiUserListDTO;
import demo.ai.manager.pojo.result.GetAiUserListResult;
import demo.ai.manager.service.AiUserManagerService;
import demo.common.controller.CommonController;

@Controller
@RequestMapping(value = AiManagerUrlConstant.ROOT)
public class AiManagerController extends CommonController {

	@Autowired
	private AiUserService userService;
	@Autowired
	private AiUserManagerService userManagerService;
	@Autowired
	private AiChatService chatService;
	@Autowired
	private AiChatCacheService aiChatCacheService;
	@Autowired
	private AiUserService aiChatUserService;

	@PostMapping(value = AiManagerUrlConstant.BLOCK_USER_BY_PK)
	@ResponseBody
	public CommonResult blockUser(@RequestBody BasePkDTO dto) {
		return userManagerService.blockUserByPk(dto.getPk());
	}

	@PostMapping(value = AiManagerUrlConstant.UNLOCK_USER_BY_PK)
	@ResponseBody
	public CommonResult unlockUserByPk(@RequestBody BasePkDTO dto) {
		return userManagerService.unlockUserByPk(dto.getPk());
	}

	@PostMapping(value = AiManagerUrlConstant.UNWARNING_USER_BY_PK)
	@ResponseBody
	public CommonResult cleanUserWarningMark(@RequestBody BasePkDTO dto) {
		return userService.cleanUserWarningMark(dto.getPk());
	}

	@GetMapping(value = AiManagerUrlConstant.CHECK_CHAT_HISTORY_BY_PK)
	@ResponseBody
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(
			@RequestParam(value = "aiChatUserPk") String aiChatUserPk) {
		return chatService.findChatHistoryByAiChatUserIdToFrontEnd(aiChatUserPk);
	}

	@GetMapping(value = AiManagerUrlConstant.CLEAR_MEMBERSHIP_CACHE_DATA)
	@ResponseBody
	public CommonResult cleanMembershipCacheData() {
		aiChatCacheService.setMembershipCacheMap(new HashMap<>());
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@PostMapping(value = AiManagerUrlConstant.USER_LIST)
	@ResponseBody
	public GetAiUserListResult userList(@RequestBody GetAiUserListDTO dto) {
		return userManagerService.getAiChatUserList(dto);
	}

	@GetMapping(value = AiManagerUrlConstant.USER_LIST)
	public ModelAndView userListView() {
		return aiChatUserService.getAiChatUserListView();
	}

	@PostMapping(value = AiManagerUrlConstant.EDIT_NICKNAME)
	@ResponseBody
	public CommonResult userList(@RequestBody AiChatUserEditNicknameDTO dto) {
		return aiChatUserService.editNickname(dto);
	}

	@GetMapping(value = AiManagerUrlConstant.UPDATE_USED_TOKEN_TO_DETAIL_IN_JSON)
	@ResponseBody
	public String updateUsedTokenToDetailInJson() {
		aiChatUserService.updateUsedTokenToDetailInJson();
		return "done";
	}

	@GetMapping(value = AiManagerUrlConstant.RECHARGE_BY_ADMIN)
	@ResponseBody
	public String rechargeByAdmin(@RequestParam("amount") Integer amount,
			@RequestParam("amountType") Integer amountType, @RequestParam("aiUserId") String userIdStr) {
		try {
			aiChatUserService.recharge(Long.parseLong(userIdStr), AiServiceAmountType.getType(amountType),
					new BigDecimal(amount));
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return "done";
	}

}
