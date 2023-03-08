package demo.thirdPartyAPI.openAI.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.thirdPartyAPI.openAI.pojo.dto.OpanAiChatCompletionMessageDTO;

public class OpenAiChatCompletionSendMessageResult extends CommonResult {

	private OpanAiChatCompletionMessageDTO dto;

	public OpanAiChatCompletionMessageDTO getDto() {
		return dto;
	}

	public void setDto(OpanAiChatCompletionMessageDTO dto) {
		this.dto = dto;
	}

	@Override
	public String toString() {
		return "OpenAiChatCompletionSendMessageResult [dto=" + dto + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", isFail()="
				+ isFail() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}

}
