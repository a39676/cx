package demo.aiChat.pojo.dto;

public class AiChatUserMembershipLevelDetailDTO {

	private Integer level;
	private Integer chatHistoryCountLimit;
	private Integer dailyBonus;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getChatHistoryCountLimit() {
		return chatHistoryCountLimit;
	}

	public void setChatHistoryCountLimit(Integer chatHistoryCountLimit) {
		this.chatHistoryCountLimit = chatHistoryCountLimit;
	}

	public Integer getDailyBonus() {
		return dailyBonus;
	}

	public void setDailyBonus(Integer dailyBonus) {
		this.dailyBonus = dailyBonus;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipLevelDetailDTO [level=" + level + ", chatHistoryCountLimit=" + chatHistoryCountLimit
				+ ", dailyBonus=" + dailyBonus + "]";
	}

}
