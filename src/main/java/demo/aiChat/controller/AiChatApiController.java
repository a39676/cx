package demo.aiChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.aiChat.pojo.constant.AiChatApiUrlConstant;
import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import demo.aiChat.service.AiChatFromApiService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = AiChatApiUrlConstant.ROOT)
public class AiChatApiController {

	@Autowired
	private AiChatFromApiService aiChatFromApiService;

	@PostMapping(value = AiChatApiUrlConstant.SEND_CHAT_COMPLETION)
	@ResponseBody
	public JSONObject sendChatCompletion(@RequestBody AiChatSendNewMsgFromApiDTO dto) {
		return aiChatFromApiService.sendNewChatMessage(dto);
	}
}
