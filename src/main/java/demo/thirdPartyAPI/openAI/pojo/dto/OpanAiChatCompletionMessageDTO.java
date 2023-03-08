package demo.thirdPartyAPI.openAI.pojo.dto;

import demo.thirdPartyAPI.openAI.pojo.type.OpenAiChatCompletionMessageRoleType;

public class OpanAiChatCompletionMessageDTO {

	/** {@link OpenAiChatCompletionMessageRoleType} */
	private String role;
	private String content;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "OpanAiChatCompletionMessageDTO [role=" + role + ", content=" + content + "]";
	}

}
