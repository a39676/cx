package demo.interaction.wechat.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.mapper.WechatAccessTokenMapper;
import demo.interaction.wechat.pojo.po.WechatAccessToken;
import demo.interaction.wechat.service.WechatTokenService;
import wechatSdk.pojo.dto.SaveAccessTokenDTO;
import wechatSdk.pojo.result.GetWechatAccessTokenResult;

@Service
public class WechatTokenServiceImpl extends WechatCommonService implements WechatTokenService {

	@Autowired
	private WechatAccessTokenMapper accessTokenMapper;

	@Override
	public EncryptDTO updateAccessToken(EncryptDTO encryptedDTO) {
		CommonResult r = new CommonResult();
		EncryptDTO encryptedResult = null;

		SaveAccessTokenDTO dto = decryptEncryptDTO(encryptedDTO, SaveAccessTokenDTO.class);
		if (dto == null) {
			r.setMessage("DTO decrypt fail or parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		if (StringUtils.isAnyBlank(dto.getAccessToken(), dto.getAppId())) {
			r.setMessage("Parameter error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		WechatAccessToken po = accessTokenMapper.selectByPrimaryKey(dto.getAppId());
		int updateCount = 0;
		if (po == null) {
			po = new WechatAccessToken();
			po.setAppId(dto.getAppId());
			po.setToken(dto.getAccessToken());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = accessTokenMapper.insert(po);
		} else {
			po.setToken(dto.getAccessToken());
			po.setCreateTime(LocalDateTime.now().minusMinutes(5));
			updateCount = accessTokenMapper.updateByPrimaryKey(po);
		}

		if (updateCount < 1) {
			r.setMessage("Saving error");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		encryptedResult = encryptDTO(r);
		return encryptedResult;

	}

	@Override
	public EncryptDTO getAccessToken(EncryptDTO encryptDTO) {
		GetWechatAccessTokenResult r = new GetWechatAccessTokenResult();
		EncryptDTO encryptedResult = null;
		
		String appId = decryptEncryptDTO(encryptDTO, String.class);
		if(StringUtils.isBlank(appId)) {
			r.setMessage("Token NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}
		
		WechatAccessToken po = accessTokenMapper.selectByPrimaryKey(appId);
		if (po == null) {
			r.setMessage("Token NOT exists, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		LocalDateTime now = LocalDateTime.now();
		if (po.getCreateTime().plusSeconds(wechatOptionService.getAccessTokenLivingSecond()).isBefore(now)) {
			r.setMessage("Token expired, please update it by yourself");
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}

		r.setIsSuccess();
		r.setAccessToken(po.getToken());
		encryptedResult = encryptDTO(r);
		return encryptedResult;

	}
}
