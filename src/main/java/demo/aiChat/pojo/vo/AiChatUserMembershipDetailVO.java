package demo.aiChat.pojo.vo;

import java.time.LocalDateTime;

public class AiChatUserMembershipDetailVO {

	private String pk;
	private String description;
	private Integer chatHistoryCountLimit;
	private Integer dailyBonus;
	private LocalDateTime expiredDateTime;
	private String expiredDateTimeStr;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public LocalDateTime getExpiredDateTime() {
		return expiredDateTime;
	}

	public void setExpiredDateTime(LocalDateTime expiredDateTime) {
		this.expiredDateTime = expiredDateTime;
	}

	public String getExpiredDateTimeStr() {
		return expiredDateTimeStr;
	}

	public void setExpiredDateTimeStr(String expiredDateTimeStr) {
		this.expiredDateTimeStr = expiredDateTimeStr;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipDetailVO [pk=" + pk + ", description=" + description + ", chatHistoryCountLimit="
				+ chatHistoryCountLimit + ", dailyBonus=" + dailyBonus + ", expiredDateTime=" + expiredDateTime
				+ ", expiredDateTimeStr=" + expiredDateTimeStr + "]";
	}

}
