package demo.interaction.wechat.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import ai.aiChat.pojo.result.AiChatSendNewMessageResult;
import ai.aiChat.pojo.result.GetAiChatHistoryResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.ai.aiArt.service.AiArtService;
import demo.ai.aiChat.service.AiChatFromWechatService;
import demo.ai.aiChat.service.impl.AiChatOptionService;
import demo.interaction.wechat.service.WechatAiService;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class WechatAiServiceImpl extends WechatCommonService implements WechatAiService {

	@Autowired
	private AiChatFromWechatService aiChatService;
	@Autowired
	private AiChatOptionService aiChatOptionService;
	@Autowired
	private AiArtService aiArtService;

	@Override
	public EncryptDTO sendNewMessage(EncryptDTO encryptedDTO) {
		AiChatSendNewMessageResult r = null;
		EncryptDTO encryptedResult = null;
		AiChatSendNewMsgFromWechatDTO dto = decryptEncryptDTO(encryptedDTO, AiChatSendNewMsgFromWechatDTO.class);
		if (dto == null) {
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
		encryptedResult = encryptDTO(getHistoryResult);
		return encryptedResult;
	}

	@Override
	public EncryptDTO getPromptOfActAs() {
		Map<String, String> promptOfActAs = aiChatOptionService.getPromptOfActAs();
		return encryptDTO(promptOfActAs);
	}

	@Override
	public EncryptDTO sendTextToImg(EncryptDTO encryptedDTO) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();
		TextToImageFromWechatDTO dto = decryptEncryptDTO(encryptedDTO, TextToImageFromWechatDTO.class);
		if (dto == null) {
			r.setMessage("Decrypt error");
			return encryptDTO(r);
		}
		r = aiArtService.sendTextToImgFromWechatDtoToMq(dto);
		return encryptDTO(r);
	}
	
	@Override
	public EncryptDTO getJobResultListByTmpKey(EncryptDTO encryptedDTO) {
		GetJobResultList r = new GetJobResultList();
		String tmpKey = decryptEncryptDTO(encryptedDTO, String.class);
		if (tmpKey == null) {
			r.setMessage("Decrypt error");
			return encryptDTO(r);
		}
		r = aiArtService.getJobResultListByTmpKey(tmpKey);
		return encryptDTO(r);
	}
}
