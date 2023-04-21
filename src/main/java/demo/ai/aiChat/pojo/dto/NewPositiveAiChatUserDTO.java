package demo.ai.aiChat.pojo.dto;

public class NewPositiveAiChatUserDTO {

	private Long aiChatUserId;
	private Long wechatUserId;
	private String openId;

	public Long getAiChatUserId() {
		return aiChatUserId;
	}

	public void setAiChatUserId(Long aiChatUserId) {
		this.aiChatUserId = aiChatUserId;
	}

	public Long getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(Long wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "NewPositiveAiChatUserDTO [aiChatUserId=" + aiChatUserId + ", wechatUserId=" + wechatUserId + ", openId="
				+ openId + "]";
	}

}
