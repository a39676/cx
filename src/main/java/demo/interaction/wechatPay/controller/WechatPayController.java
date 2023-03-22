package demo.interaction.wechatPay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.interaction.wechatPay.service.WechatPayService;
import wechatPaySdk.jsApi.pojo.constant.WechatPaySdkUrlConstant;

@Controller
@RequestMapping(value = WechatPaySdkUrlConstant.ROOT)
public class WechatPayController {

	@Autowired
	private WechatPayService wechatPayService;

	@PostMapping(value = WechatPaySdkUrlConstant.GET_OPTION)
	@ResponseBody
	public EncryptDTO getOption(@RequestBody EncryptDTO dto) {
		return wechatPayService.getWechatPayOptionDTO(dto);
	}

	@PostMapping(value = WechatPaySdkUrlConstant.GET_TICKET)
	@ResponseBody
	public EncryptDTO getJsApiTicket(@RequestBody EncryptDTO dto) {
		return wechatPayService.getJsApiTicket(dto);
	}

	@PostMapping(value = WechatPaySdkUrlConstant.UPDATE_TICKET)
	@ResponseBody
	public EncryptDTO updateJsApiTicket(@RequestBody EncryptDTO dto) {
		return wechatPayService.updateJsApiTicket(dto);
	}

}
