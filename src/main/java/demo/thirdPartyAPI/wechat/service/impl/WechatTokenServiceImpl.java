package demo.thirdPartyAPI.wechat.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.thirdPartyAPI.wechat.mapper.WechatAccessTokenMapper;
import demo.thirdPartyAPI.wechat.pojo.po.WechatAccessToken;
import demo.thirdPartyAPI.wechat.service.WechatTokenService;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;
import wechat.pojo.constant.WechatSdkUrlConstant;
import wechat.pojo.dto.GetAccessTokenDTO;
import wechat.pojo.result.GetWechatAccessTokenResult;

@Service
public class WechatTokenServiceImpl extends WechatCommonService implements WechatTokenService {

	@Autowired
	private WechatAccessTokenMapper accessTokenMapper;

	@Override
	public void updateAccessToken() {
		String appId = wechatOptionService.getAppId1();
		String secret = wechatOptionService.getAppSecret1();
		String url = wechatOptionService.getSdkMainUrl() + WechatSdkUrlConstant.ROOT
				+ WechatSdkUrlConstant.UPDATE_ACCESS_TOKEN;
		HttpUtil h = new HttpUtil();

		GetAccessTokenDTO dto = new GetAccessTokenDTO();
		dto.setAppId(appId);
		dto.setAppSecret(secret);

		JSONObject json = JSONObject.fromObject(dto);

		String response = null;
		try {
			response = h.sendPostRestful(url, json.toString());
		} catch (Exception e) {
			log.error("Update access token error, appId: " + appId + ", e: " + e.getLocalizedMessage());
		}

		try {
			GetWechatAccessTokenResult result = new Gson().fromJson(response, GetWechatAccessTokenResult.class);
			if (result.isFail()) {
				sendTelegramMessage("Update wechat access token failed, appId: " + appId + ", msg: "
						+ result.getMessage() + ", code: " + result.getCode());
			}

			WechatAccessToken po = accessTokenMapper.selectByPrimaryKey(appId);
			if (po == null) {
				po = new WechatAccessToken();
				po.setAppId(appId);
				po.setToken(result.getAccessToken());
				accessTokenMapper.insertSelective(po);
			} else {
				po.setToken(result.getAccessToken());
				po.setCreateTime(LocalDateTime.now());
				accessTokenMapper.updateByPrimaryKeySelective(po);
			}
		} catch (Exception e) {
			sendTelegramMessage(
					"Update wechat access token failed, appId: " + appId + ", msg: " + e.getLocalizedMessage());
		}
	}

	@Override
	public String getAccessToken() {
		String appId = wechatOptionService.getAppId1();

		WechatAccessToken po = accessTokenMapper.selectByPrimaryKey(appId);
		if (po == null) {
			updateAccessToken();
			po = accessTokenMapper.selectByPrimaryKey(appId);
			if (po == null) {
				return null;
			}
			
			return po.getToken();
		}
		
		LocalDateTime now = LocalDateTime.now();
		if(po.getCreateTime().plusSeconds(wechatOptionService.getAccessTokenLivingSecond()).isBefore(now)) {
			updateAccessToken();
			po = accessTokenMapper.selectByPrimaryKey(appId);
			if (po == null) {
				return null;
			}
			
			return po.getToken();
		}
		
		return po.getToken();
	}
}
