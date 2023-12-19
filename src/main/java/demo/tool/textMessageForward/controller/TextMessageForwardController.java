package demo.tool.textMessageForward.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.textMessageForward.service.TextMessageForwardService;
import tool.pojo.constant.TextMessageForwardUrl;

@Controller
@RequestMapping(value = TextMessageForwardUrl.ROOT)
public class TextMessageForwardController {

	@Autowired
	private TextMessageForwardService msgForwardServcie;
	
	@PostMapping(value = TextMessageForwardUrl.FROM_BBT)
	@ResponseBody
	public CommonResult msgFromBbt(ServiceMsgDTO dto) {
		return msgForwardServcie.textMessageForwarding(dto);
	}
}
