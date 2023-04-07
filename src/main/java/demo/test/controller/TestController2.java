package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.service.AiChatService;
import demo.common.service.CommonService;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.test.service.TestService2;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonService {

	@Autowired
	private TestService2 testService2;

	@GetMapping(value = "/t1")
	public ModelAndView testView() {
		return testService2.testView();
	}

	@PostMapping(value = "/t2")
	@ResponseBody
	public String t2(@RequestBody TestDTO dto) {
		return "{\"k\":\"v\"}";
	}

	@Autowired
	private AiChatService aiChatService;

	@GetMapping(value = "/t3")
	@ResponseBody
	public AiChatSendNewMessageResult t3(@RequestParam(value = "userId", defaultValue = "1") Long userId,
			@RequestParam(value = "msg", defaultValue = "test") String msg,
			@RequestParam(value = "counter", defaultValue = "6") Integer counter) {
		AiChatSendNewMsgFromWechatDTO dto = new AiChatSendNewMsgFromWechatDTO();
		dto.setChatModeCounter(counter);
		dto.setMsg(msg);
		return aiChatService.sendNewChatMessageWithHistory(userId, dto);
	}
}
