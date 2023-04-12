package demo.aiChat.pojo.vo;

public class AiChatUserVO {

	private String userPk;
	private String nickname;
	private String wechatUserPk;
	private Double bonusAmount;
	private Double rechargeAmount;
	private String createTime;
	private String lastUpdateTime;
	private Integer usedTokens;
	private Boolean isDelete;
	private Boolean isBlock;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWechatUserPk() {
		return wechatUserPk;
	}

	public void setWechatUserPk(String wechatUserPk) {
		this.wechatUserPk = wechatUserPk;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getUsedTokens() {
		return usedTokens;
	}

	public void setUsedTokens(Integer usedTokens) {
		this.usedTokens = usedTokens;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(Boolean isBlock) {
		this.isBlock = isBlock;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "AiChatUserVO [userPk=" + userPk + ", nickname=" + nickname + ", wechatUserPk=" + wechatUserPk
				+ ", bonusAmount=" + bonusAmount + ", rechargeAmount=" + rechargeAmount + ", createTime=" + createTime
				+ ", lastUpdateTime=" + lastUpdateTime + ", usedTokens=" + usedTokens + ", isDelete=" + isDelete
				+ ", isBlock=" + isBlock + "]";
	}

}
