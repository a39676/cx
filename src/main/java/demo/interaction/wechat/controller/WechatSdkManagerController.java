package demo.interaction.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.service.WechatSdkManagerService;
import demo.interaction.wechat.service.WechatTokenService;
import wechatSdk.pojo.constant.WechatSdkUrlConstant;

@Controller
@RequestMapping(value = WechatSdkUrlConstant.WECHAT_MANAGER)
public class WechatSdkManagerController {

	@Autowired
	private WechatSdkManagerService wechatSdkManagerService;
	@Autowired
	private WechatTokenService tokenService;

	@GetMapping(value = "/refreshWechatSdkWechatOption")
	@ResponseBody
	public CommonResult refreshWechatSdkWechatOption() {
		return wechatSdkManagerService.refreshWechatSdkWechatOption();
	}

	@GetMapping(value = WechatSdkUrlConstant.GET_WECHAT_SDK_WECHAT_OPTION)
	@ResponseBody
	public EncryptDTO getWechatSdkWechatOption() {
		return wechatSdkManagerService.getWechatSdkWechatOption();
	}

	@PostMapping(value = WechatSdkUrlConstant.UPDATE_ACCESS_TOKEN)
	@ResponseBody
	public EncryptDTO updateAccessToken(@RequestBody EncryptDTO dto) {
		return tokenService.updateAccessToken(dto);
	}

	@PostMapping(value = WechatSdkUrlConstant.GET_WECHAT_ACCESS_TOKEN)
	@ResponseBody
	public EncryptDTO getAccessToken(@RequestBody EncryptDTO dto) {
		return tokenService.getAccessToken(dto);
	}

}
