package demo.ai.aiChat.pojo.dto;

public class AiChatUserMembershipDetailDTO {

	private Long id;
	private String description;
	private Integer chatHistoryCountLimit;
	private Integer dailyBonus;
	private Integer bonus;
	private Integer recharge;
	private Integer effectiveDays;
	private Double price;
	private boolean isNormalPlan = false;
	private Integer validDays;

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

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	public boolean getIsNormalPlan() {
		return isNormalPlan;
	}

	public void setIsNormalPlan(boolean isNormalPlan) {
		this.isNormalPlan = isNormalPlan;
	}

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	@Override
	public String toString() {
		return "AiChatUserMembershipDetailDTO [id=" + id + ", description=" + description + ", chatHistoryCountLimit="
				+ chatHistoryCountLimit + ", dailyBonus=" + dailyBonus + ", bonus=" + bonus + ", recharge=" + recharge
				+ ", effectiveDays=" + effectiveDays + ", price=" + price + ", isNormalPlan=" + isNormalPlan
				+ ", validDays=" + validDays + "]";
	}

}
