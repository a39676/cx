package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.mq.producer.TelegramMessageAckProducer;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;
import telegram.pojo.dto.TelegramMessageDTO;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;

	@Autowired
	private TelegramMessageAckProducer telegramMessageAckProducer;
	
	@GetMapping(value = "/telTest")
	public void telTest(@RequestParam(value = "msg") String msg, @RequestParam(value = "id") Long id) {
		TelegramMessageDTO dto = new TelegramMessageDTO();
		dto.setMsg(msg);
		dto.setId(id);
		telegramMessageAckProducer.send(dto);
	}
}
