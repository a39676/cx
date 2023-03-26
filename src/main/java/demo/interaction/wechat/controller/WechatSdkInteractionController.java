package demo.interaction.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aiChat.pojo.constant.AiChatFromWechatSdkUrlConstant;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.common.controller.CommonController;
import demo.interaction.wechat.service.WechatAiChatService;
import demo.interaction.wechat.service.WechatUserService;

@Controller
@RequestMapping(value = AiChatFromWechatSdkUrlConstant.ROOT)
public class WechatSdkInteractionController extends CommonController {

	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private WechatAiChatService wechatAiChatService;

	@PostMapping(value = AiChatFromWechatSdkUrlConstant.GET_TMP_KEY_BY_OPEN_ID)
	@ResponseBody
	public EncryptDTO getTmpKeyByOpenId(@RequestBody EncryptDTO dto) {
		return wechatUserService.getTmpKeyByOpenId(dto);
	}
	
	@PostMapping(value = AiChatFromWechatSdkUrlConstant.SEND_NEW_MESSAGE)
	@ResponseBody
	public EncryptDTO sendNewMessage(@RequestBody EncryptDTO dto) {
		return wechatAiChatService.sendNewMessage(dto);
	}

	@PostMapping(value = AiChatFromWechatSdkUrlConstant.GET_CHAT_HISTORY)
	@ResponseBody
	public EncryptDTO getChatHistory(@RequestBody EncryptDTO dto) {
		return wechatAiChatService.findChatHistoryByAiChatUserIdToFrontEnd(dto);
	}
	
	@PostMapping(value = AiChatFromWechatSdkUrlConstant.EXTEND_TMP_KEY_VALIDITY)
	public void extendTmpKeyValidity(@RequestBody EncryptDTO dto) {
		wechatUserService.extendTmpKeyValidity(dto);
	}
	
	@PostMapping(value = AiChatFromWechatSdkUrlConstant.BUY_MEMBERSHIP_FROM_WECHAT)
	@ResponseBody
	public EncryptDTO buyMembershipFromWechat(@RequestBody EncryptDTO dto) {
		return wechatUserService.buyMembershipFromWechat(dto);
	}
	
	@PostMapping(value = AiChatFromWechatSdkUrlConstant.GET_MY_DETAIL)
	@ResponseBody
	public EncryptDTO getMembershipListFromWechat(@RequestBody EncryptDTO dto) {
		return wechatUserService.getAiChatUserDetail(dto);
	}
	
	@PostMapping(value = AiChatFromWechatSdkUrlConstant.DAILY_SIGN_UP)
	@ResponseBody
	public EncryptDTO dailySignUp(@RequestBody EncryptDTO dto) {
		return wechatUserService.dailySignUp(dto);
	}
	
}
