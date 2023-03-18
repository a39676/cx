package demo.interaction.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aiChat.pojo.constant.AiChatFromWechatSdkUrlConstant;
import aiChat.pojo.result.GetTmpKeyByOpenIdResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.common.controller.CommonController;
import demo.interaction.wechat.service.WechatUserService;

@Controller
@RequestMapping(value = AiChatFromWechatSdkUrlConstant.ROOT)
public class WechatSdkInteractionController extends CommonController {

	@Autowired
	private WechatUserService wechatUserService;

	@PostMapping(value = AiChatFromWechatSdkUrlConstant.GET_TMP_KEY_BY_OPEN_ID)
	@ResponseBody
	public GetTmpKeyByOpenIdResult getTmpKeyByOpenId(@RequestBody EncryptDTO dto) {
		return wechatUserService.getTmpKeyByOpenId(dto);
	}

}
