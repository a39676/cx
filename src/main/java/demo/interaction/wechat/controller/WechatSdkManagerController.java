package demo.interaction.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.service.WechatNotifyService;
import demo.interaction.wechat.service.WechatSdkManagerService;
import demo.interaction.wechat.service.WechatTokenService;
import demo.interaction.wechat.service.WechatUserService;
import wechatSdk.pojo.constant.WechatSdkUrlConstant;

@Controller
@RequestMapping(value = WechatSdkUrlConstant.ROOT)
public class WechatSdkManagerController {

	@Autowired
	private WechatSdkManagerService wechatSdkManagerService;
	@Autowired
	private WechatTokenService tokenService;
	@Autowired
	private WechatNotifyService notifyService;
	@Autowired
	private WechatUserService wechatUserService;

	@PostMapping(value = WechatSdkUrlConstant.GET_WECHAT_SDK_WECHAT_OPTION)
	@ResponseBody
	public EncryptDTO getWechatSdkWechatOption(@RequestBody EncryptDTO dto) {
		return wechatSdkManagerService.getWechatSdkWechatOption(dto);
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

	@PostMapping(value = WechatSdkUrlConstant.SEND_NOTIFY)
	public void forwardNotifyFromWechatSdk(@RequestBody EncryptDTO dto) {
		notifyService.forwardNotifyFromWechatSdk(dto);
	}

	@GetMapping(value = WechatSdkUrlConstant.REG_OPEN_ID_MANUAL)
	@ResponseBody
	public CommonResult __regOpenIdManual(@RequestParam("openId") String openId) {
		return wechatUserService.__regOpenIdManual(openId);
	}

	@GetMapping(value = WechatSdkUrlConstant.COMPARE_LOCAL_OPEN_ID_LIST_WITH_WECHAT_OPEN_ID_LIST)
	@ResponseBody
	public String compareLocalOpenIdListWithWechatOpenIdList() {
		wechatUserService.compareLocalOpenIdListWithWechatOpenIdList();
		return "Done";
	}
}
