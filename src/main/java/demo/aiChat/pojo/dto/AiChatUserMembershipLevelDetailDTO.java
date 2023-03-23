package demo.aiChat.pojo.dto;

public class AiChatUserMembershipLevelDetailDTO {

	private Long id;
	private String description;
	private Long level;
	private Integer chatHistoryCountLimit;
	private Integer dailyBonus;
	private Integer recharge;
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getRecharge() {
		return recharge;
	}

	public void setRecharge(Integer recharge) {
		this.recharge = recharge;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipLevelDetailDTO [id=" + id + ", description=" + description + ", level=" + level
				+ ", chatHistoryCountLimit=" + chatHistoryCountLimit + ", dailyBonus=" + dailyBonus + ", recharge="
				+ recharge + ", price=" + price + "]";
	}

}
