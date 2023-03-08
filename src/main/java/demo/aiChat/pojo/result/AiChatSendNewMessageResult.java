package demo.aiChat.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionMessageDTO;
import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionFinishType;

public class AiChatSendNewMessageResult extends CommonResult {

	private OpanAiChatCompletionMessageDTO msgDTO;

	private OpenAiChatCompletionFinishType finishType;

	public OpanAiChatCompletionMessageDTO getMsgDTO() {
		return msgDTO;
	}

	public void setMsgDTO(OpanAiChatCompletionMessageDTO msgDTO) {
		this.msgDTO = msgDTO;
	}

	public OpenAiChatCompletionFinishType getFinishType() {
		return finishType;
	}

	public void setFinishType(OpenAiChatCompletionFinishType finishType) {
		this.finishType = finishType;
	}

	@Override
	public String toString() {
		return "AiChatSendNewMessageResult [msgDTO=" + msgDTO + ", finishType=" + finishType + "]";
	}

}
