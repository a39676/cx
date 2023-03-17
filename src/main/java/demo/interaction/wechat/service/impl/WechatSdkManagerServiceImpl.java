package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.service.WechatSdkManagerService;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;
import wechatSdk.pojo.constant.WechatSdkUrlConstant;
import wechatSdk.pojo.dto.UpdateWechatOptionDTO;

@Service
public class WechatSdkManagerServiceImpl extends WechatCommonService implements WechatSdkManagerService {

	@Autowired
	private WechatOptionService wechatOptionService;

	@Override
	public CommonResult refreshWechatSdkWechatOption() {
		CommonResult r = new CommonResult();
		HttpUtil h = new HttpUtil();
		String sdkMainUrl = wechatOptionService.getSdkMainUrl();
		String url = sdkMainUrl + WechatSdkUrlConstant.WECHAT_MANAGER + WechatSdkUrlConstant.WECHAT_OPTION_REFRESH;
		UpdateWechatOptionDTO dto = new UpdateWechatOptionDTO();
		dto.setAppId(wechatOptionService.getAppId1());
		dto.setAppSecret(wechatOptionService.getAppSecret1());

		try {
			JSONObject json = JSONObject.fromObject(dto);
			String jsonStr = json.toString();
			String encryptedStr = encryptUtil.aesEncrypt(systemOptionService.getAesKey(),
					systemOptionService.getAesInitVector(), jsonStr);
			EncryptDTO encryptedDTO = new EncryptDTO();
			encryptedDTO.setEncryptedStr(encryptedStr);
			String response = h.sendPostRestful(url, json.toString());
			CommonResult feedback = buildObjFromJsonCustomization(response, CommonResult.class);
			r.setSuccess(feedback.isSuccess());
			r.setMessage(feedback.getMessage());
			r.setCode(feedback.getCode());
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}

	@Override
	public EncryptDTO getWechatSdkWechatOption() {
		UpdateWechatOptionDTO dto = new UpdateWechatOptionDTO();
		dto.setAppId(wechatOptionService.getAppId1());
		dto.setAppSecret(wechatOptionService.getAppSecret1());

		return encryptDTO(dto);
	}
}
