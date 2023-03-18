package demo.aiChat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.service.AiChatFromWechatService;
import demo.aiChat.service.AiChatService;

@Service
public class AiChatFromWechatServiceImpl extends AiChatCommonService implements AiChatFromWechatService {

	@Autowired
	private AiChatService aiChatService;

	@Override
	public AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgDTO dto) {
		AiChatSendNewMessageResult r = null;

		if (StringUtils.isBlank(dto.getTemporaryKey())) {
			r = new AiChatSendNewMessageResult();
			r.setMessage("Temporary key expired");
			return r;
		}

		Long tmpK = null;
		try {
			tmpK = Long.parseLong(dto.getTemporaryKey());
		} catch (Exception e) {
			r = new AiChatSendNewMessageResult();
			r.setMessage("Temporary key expired");
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpK);
		if (aiChatUserId == null) {
			r = new AiChatSendNewMessageResult();
			r.setMessage("Temporary key expired");
			return r;
		}
		
		tmpKeyInsertOrUpdateLiveTime(tmpK, aiChatUserId);

		r = aiChatService.sendNewChatMessage(aiChatUserId, dto.getMsg());
		return r;
	}

}
