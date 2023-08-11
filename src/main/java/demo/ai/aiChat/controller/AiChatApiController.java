package demo.ai.aiChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.aiChat.pojo.constant.AiChatApiUrlConstant;
import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import demo.ai.aiChat.service.AiChatFromApiService;
import openAi.pojo.dto.OpenAiChatCompletionResponseDTO;

@Controller
@RequestMapping(value = AiChatApiUrlConstant.ROOT)
public class AiChatApiController {

	@Autowired
	private AiChatFromApiService aiChatFromApiService;

	@PostMapping(value = AiChatApiUrlConstant.SEND_CHAT_COMPLETION)
	@ResponseBody
	public OpenAiChatCompletionResponseDTO sendChatCompletion(@RequestBody AiChatSendNewMsgFromApiDTO dto) {
		return aiChatFromApiService.sendNewChatMessage(dto);
	}
}
