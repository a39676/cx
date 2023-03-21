package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.aiChat.service.AiChatFromWechatService;
import demo.interaction.wechat.service.WechatAiChatService;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class WechatAiChatServiceImpl extends WechatCommonService implements WechatAiChatService {

	@Autowired
	private AiChatFromWechatService aiChatService;
	
	@Override
	public EncryptDTO sendNewMessage(EncryptDTO encryptedDTO) {
		AiChatSendNewMessageResult r = null;
		EncryptDTO encryptedResult = null;
		AiChatSendNewMsgDTO dto = decryptEncryptDTO(encryptedDTO, AiChatSendNewMsgDTO.class);
		if(dto == null) {
			r = new AiChatSendNewMessageResult();
			r.setResultByType(WechatSdkCommonResultType.TMP_KEY_EXPIRED);
			encryptedResult = encryptDTO(r);
			return encryptedResult;
		}
		r = aiChatService.sendNewChatFromWechat(dto);
		encryptedResult = encryptDTO(r);
		return encryptedResult;
	}

	@Override
	public EncryptDTO findChatHistoryByAiChatUserIdToFrontEnd(EncryptDTO encryptedDTO) {
		EncryptDTO encryptedResult = null;
		String tmpKeyStr = decryptEncryptDTO(encryptedDTO, String.class);
		
		GetAiChatHistoryResult getHistoryResult = aiChatService.findChatHistoryByAiChatUserIdToFrontEnd(tmpKeyStr);
		encryptedResult =  encryptDTO(getHistoryResult);
		return encryptedResult;
	}
	
}
