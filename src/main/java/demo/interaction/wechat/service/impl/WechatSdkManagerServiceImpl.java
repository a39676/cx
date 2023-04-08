package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.interaction.wechat.service.WechatSdkManagerService;
import wechatSdk.pojo.dto.UpdateWechatOptionDTO;

@Service
public class WechatSdkManagerServiceImpl extends WechatCommonService implements WechatSdkManagerService {

	@Autowired
	private WechatOptionService wechatOptionService;

	@Override
	public EncryptDTO getWechatSdkWechatOption(EncryptDTO encryptedDTO) {
		String managerCode = decryptEncryptDTO(encryptedDTO, String.class);
		UpdateWechatOptionDTO dto = new UpdateWechatOptionDTO();
		if(!wechatOptionService.getManagerCode().equals(managerCode)) {
			return encryptDTO(dto);
		}
		dto.setAppId1(wechatOptionService.getAppId1());
		dto.setAppSecret1(wechatOptionService.getAppSecret1());
		dto.setOrignOpenId1(wechatOptionService.getOriginOpenId1());

		return encryptDTO(dto);
	}
	
}
