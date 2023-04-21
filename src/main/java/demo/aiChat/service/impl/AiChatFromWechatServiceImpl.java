package demo.aiChat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import ai.aiChat.pojo.result.AiChatSendNewMessageResult;
import ai.aiChat.pojo.result.GetAiChatHistoryResult;
import demo.ai.common.service.impl.AiCommonService;
import demo.aiChat.service.AiChatFromWechatService;
import demo.aiChat.service.AiChatService;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class AiChatFromWechatServiceImpl extends AiCommonService implements AiChatFromWechatService {

	@Autowired
	private AiChatService aiChatService;

	@Override
	public AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgFromWechatDTO dto) {
		AiChatSendNewMessageResult r = null;

		if (StringUtils.isBlank(dto.getTemporaryKey())) {
			r = new AiChatSendNewMessageResult();
			r.setResultByType(WechatSdkCommonResultType.TMP_KEY_EXPIRED);
			return r;
		}

		Long tmpK = null;
		try {
			tmpK = Long.parseLong(dto.getTemporaryKey());
		} catch (Exception e) {
			r = new AiChatSendNewMessageResult();
			r.setResultByType(WechatSdkCommonResultType.TMP_KEY_EXPIRED);
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpK);
		if (aiChatUserId == null) {
			r = new AiChatSendNewMessageResult();
			r.setResultByType(WechatSdkCommonResultType.TMP_KEY_EXPIRED);
			return r;
		}

		tmpKeyInsertOrUpdateLiveTime(tmpK, aiChatUserId);

		r = aiChatService.sendNewChatMessageWithHistory(aiChatUserId, dto);
		if("-10".equals(r.getCode())) {
			r.setMessage("暂时无法发送消息, 请返回公众号界面联系管理员");
		}
		
		return r;
	}

	@Override
	public GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String tmpKeyStr) {
		GetAiChatHistoryResult r = null;
		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKeyStr);
		if (aiChatUserId == null) {
			r = new GetAiChatHistoryResult();
			r.setMessage("登录过期, 请重新登录后再试");
			return r;
		}

		r = aiChatService.findChatHistoryByAiChatUserIdToFrontEnd(aiChatUserId);
		return r;
	}

	
}
