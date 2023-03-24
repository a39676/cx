package demo.aiChat.pojo.dto;

public class AiChatUserMembershipDetailDTO {

	private Long id;
	private String description;
	private Integer chatHistoryCountLimit;
	private Integer dailyBonus;
	private Integer recharge;
	private Integer effectiveDays;
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

	public Integer getEffectiveDays() {
		return effectiveDays;
	}

	public void setEffectiveDays(Integer effectiveDays) {
		this.effectiveDays = effectiveDays;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipDetailDTO [id=" + id + ", description=" + description + ", chatHistoryCountLimit="
				+ chatHistoryCountLimit + ", dailyBonus=" + dailyBonus + ", recharge=" + recharge + ", effectiveDays="
				+ effectiveDays + ", price=" + price + "]";
	}

}
