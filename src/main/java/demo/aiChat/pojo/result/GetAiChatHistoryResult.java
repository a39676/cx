package demo.aiChat.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import openAi.pojo.dto.OpanAiChatCompletionMessageDTO;

public class GetAiChatHistoryResult extends CommonResult {

	private List<OpanAiChatCompletionMessageDTO> msgList;

	public List<OpanAiChatCompletionMessageDTO> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<OpanAiChatCompletionMessageDTO> msgList) {
		this.msgList = msgList;
	}

	@Override
	public String toString() {
		return "GetAiChatHistoryResult [msgList=" + msgList + "]";
	}

}
