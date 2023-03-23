package demo.aiChat.pojo.dto;

import java.util.HashMap;
import java.util.Map;

import demo.aiChat.pojo.vo.AiChatUserMembershipDetailVO;

public class AiChatUserMembershipDetailSummaryDTO {

	private Long aiChatUserId;
	private String description;
	private Long level;
	private Integer chatHistoryCountLimit = 0;
	private Integer dailyBonus = 0;
	private Map<Long, AiChatUserMembershipDetailVO> membershipMap = new HashMap<>();

	public Long getAiChatUserId() {
		return aiChatUserId;
	}

	public void setAiChatUserId(Long aiChatUserId) {
		this.aiChatUserId = aiChatUserId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
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

	public Map<Long, AiChatUserMembershipDetailVO> getMembershipMap() {
		return membershipMap;
	}

	public void setMembershipMap(Map<Long, AiChatUserMembershipDetailVO> membershipMap) {
		this.membershipMap = membershipMap;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipDetailSummaryDTO [aiChatUserId=" + aiChatUserId + ", description=" + description
				+ ", level=" + level + ", chatHistoryCountLimit=" + chatHistoryCountLimit + ", dailyBonus=" + dailyBonus
				+ ", membershipMap=" + membershipMap + "]";
	}

}
