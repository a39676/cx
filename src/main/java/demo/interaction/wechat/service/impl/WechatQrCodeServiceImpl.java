package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.interaction.wechat.mapper.WechatQrcodeDetailMapper;
import demo.interaction.wechat.pojo.po.WechatQrcodeDetail;
import demo.interaction.wechat.service.WechatQrCodeService;
import wechatSdk.pojo.result.CreateLongLiveQrCodeResult;
import wechatSdk.pojo.type.WechatOfficialAccountType;

@Service
public class WechatQrCodeServiceImpl extends WechatCommonService implements WechatQrCodeService {

	@Autowired
	private WechatQrcodeDetailMapper qrCodeMapper;

	@Override
	public EncryptDTO receiveLongLiveQrCodeResult(EncryptDTO encryptedDTO) {
		CommonResult result = new CommonResult();
		CreateLongLiveQrCodeResult decryptResult = decryptEncryptDTO(encryptedDTO, CreateLongLiveQrCodeResult.class);
		if (decryptResult == null) {
			result.setMessage("receive long live QR code decrypt error");
			return encryptDTO(result);
		}
		WechatOfficialAccountType officialAccountType = WechatOfficialAccountType
				.getType(decryptResult.getSourceOfficialAccountCode());
		if (officialAccountType == null) {
			result.setMessage("Receive long live QR code, parameter error");
			return encryptDTO(result);
		}

		WechatQrcodeDetail row = new WechatQrcodeDetail();
		row.setId(snowFlake.getNextId());
		row.setSceneName(decryptResult.getParameter());
		row.setUrl(decryptResult.getUrl());
		row.setSourceOfficialAccount(decryptResult.getSourceOfficialAccountCode());
		qrCodeMapper.insertSelective(row);

		result.setIsSuccess();
		return encryptDTO(result);
	}

}
