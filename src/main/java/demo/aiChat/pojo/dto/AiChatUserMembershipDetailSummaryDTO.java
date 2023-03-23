package demo.aiChat.pojo.dto;

public class AiChatUserMembershipDetailSummaryDTO extends AiChatUserMembershipDetailDTO {

	private Long aiChatUserId;

	public Long getAiChatUserId() {
		return aiChatUserId;
	}

	public void setAiChatUserId(Long aiChatUserId) {
		this.aiChatUserId = aiChatUserId;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipDetailSummaryDTO [aiChatUserId=" + aiChatUserId + ", getLevel()=" + getLevel()
				+ ", getChatHistoryCountLimit()=" + getChatHistoryCountLimit() + ", getDailyBonus()=" + getDailyBonus()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
