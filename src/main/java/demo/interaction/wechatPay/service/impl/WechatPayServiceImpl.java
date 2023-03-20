package demo.interaction.wechatPay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.interaction.wechat.service.impl.WechatCommonService;
import demo.interaction.wechatPay.service.WechatPayService;
import wechatPaySdk.jsApi.pojo.dto.WechatPayOptionDTO;

@Service
public class WechatPayServiceImpl extends WechatCommonService implements WechatPayService {

	@Autowired
	private WechatPayOptionService wechatPayOptionService;
	
	@Override
	public EncryptDTO getWechatPayOptionDTO(EncryptDTO encrypt) {
		String managerCode = decryptEncryptDTO(encrypt, String.class);
		if(!wechatOptionService.getManagerCode().equals(managerCode)) {
			return new EncryptDTO();
		}
		
		WechatPayOptionDTO dto = new WechatPayOptionDTO();
		dto.setApiV3Key(wechatPayOptionService.getApiV3Key());
		dto.setMerchantId(wechatPayOptionService.getMerchantId());
		dto.setMerchantSerialNumber(wechatPayOptionService.getMerchantSerialNumber());
		dto.setPrivateKey(wechatPayOptionService.getPrivateKey());
		return encryptDTO(dto);
	}
}
