package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.aiChat.service.AiChatUserService;
import demo.common.service.CommonService;
import demo.interaction.wechat.mq.producer.SendBonusRechargeTemplateMessageProducer;
import demo.test.pojo.constant.TestUrl;
import demo.test.pojo.dto.TestDTO;
import demo.test.service.TestService2;
import wechatSdk.pojo.dto.SendTemplateMessageBonusRechargeDTO;

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
	private SendBonusRechargeTemplateMessageProducer sender;
	@Autowired
	private AiChatUserService aiChatUserService;

	@GetMapping(value = "/t3")
	@ResponseBody
	public String t3() {
		SendTemplateMessageBonusRechargeDTO dto = new SendTemplateMessageBonusRechargeDTO();
		dto.setBonusAmountStr("bas");
		dto.setBonusDescription("bd");
		dto.setManagerCode("LqHvN5DTjlqMHtt2Jg539wDma7MlvopMnjmCp6j5g8T");
		dto.setReciverOpenId("oXt5d5l_w5Fjl7IyaQ1SF7fTjS1g");
		sender.send(dto);
		return "Done";
	}
	
	@GetMapping(value = "/t4")
	@ResponseBody
	public String t4() {
		aiChatUserService.__findNewPositiveAiChatUserDtoListInYesterday();
		return "Done";
	}
}
