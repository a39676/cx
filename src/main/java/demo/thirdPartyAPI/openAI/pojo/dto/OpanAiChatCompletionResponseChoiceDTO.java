package demo.thirdPartyAPI.openAI.pojo.dto;

import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionFinishType;

public class OpanAiChatCompletionResponseChoiceDTO {

	/** {@link OpenAiChatCompletionFinishType} */
	private String finish_reason;
	private Integer index;
	private OpanAiChatCompletionMessageDTO message;

	public OpanAiChatCompletionMessageDTO getMessage() {
		return message;
	}

	public void setMessage(OpanAiChatCompletionMessageDTO message) {
		this.message = message;
	}

	public String getFinish_reason() {
		return finish_reason;
	}

	public void setFinish_reason(String finish_reason) {
		this.finish_reason = finish_reason;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "OpanAiChatCompletionResponseChoiceDTO [finish_reason=" + finish_reason + ", index=" + index
				+ ", message=" + message + "]";
	}

}
