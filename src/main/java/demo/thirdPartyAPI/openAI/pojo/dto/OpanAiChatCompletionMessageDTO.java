package demo.thirdPartyAPI.openAI.pojo.dto;

import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionMessageRoleType;

public class OpanAiChatCompletionMessageDTO {

	private String content;
	/** {@link OpenAiChatCompletionMessageRoleType} */
	private String role;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "OpanAiChatCompletionResponseChoiceMessageDTO [content=" + content + ", role=" + role + "]";
	}

}
