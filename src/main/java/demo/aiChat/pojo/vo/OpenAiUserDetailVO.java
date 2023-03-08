package demo.aiChat.pojo.vo;

public class OpenAiUserDetailVO {

	private String userPk;

	private Double bonusAmount;

	private Double rechargeAmount;

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	@Override
	public String toString() {
		return "OpenAiUserDetailVO [userPk=" + userPk + ", bonusAmount=" + bonusAmount + ", rechargeAmount="
				+ rechargeAmount + "]";
	}

}
