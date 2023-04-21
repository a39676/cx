package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.result.AiArtImageWallResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.ai.aiArt.service.AiArtService;
import demo.interaction.wechat.service.WechatAiArtService;

@Service
public class WechatAiArtServiceImpl extends WechatCommonService implements WechatAiArtService {

	@Autowired
	private AiArtService aiArtService;

	@Override
	public EncryptDTO getImageWall(EncryptDTO encryptedDTO) {
		String randomStr = decryptEncryptDTO(encryptedDTO, String.class);
		if (randomStr == null) {
			return encryptDTO(new AiArtImageWallResult());
		}

		return encryptDTO(aiArtService.getImageWall());
	}
}
