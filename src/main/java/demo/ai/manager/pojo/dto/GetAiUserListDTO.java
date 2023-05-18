package demo.ai.manager.pojo.dto;

public class GetAiUserListDTO {

	private String startPk;
	private String nickname;
	private String createTimeMinStr;
	private String createTimeMaxStr;
	private String lastUpdateTimeMinStr;
	private String lastUpdateTimeMaxStr;
	private Boolean isDelete;
	private Boolean isBlock;
	private Boolean isWarning;
	private Double bonusAmountMin;
	private Double bonusAmountMax;
	private Double rechargeAmountMin;
	private Double rechargeAmountMax;
	private Integer usedTokensMin;
	private Integer usedTokensMax;
	private String sourceQrCodePk;
	private Integer limit;
	private String orderBy;
	private Boolean isAesc;

	public String getStartPk() {
		return startPk;
	}

	public void setStartPk(String startPk) {
		this.startPk = startPk;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCreateTimeMinStr() {
		return createTimeMinStr;
	}

	public void setCreateTimeMinStr(String createTimeMinStr) {
		this.createTimeMinStr = createTimeMinStr;
	}

	public String getCreateTimeMaxStr() {
		return createTimeMaxStr;
	}

	public void setCreateTimeMaxStr(String createTimeMaxStr) {
		this.createTimeMaxStr = createTimeMaxStr;
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

	public Double getBonusAmountMin() {
		return bonusAmountMin;
	}

	public void setBonusAmountMin(Double bonusAmountMin) {
		this.bonusAmountMin = bonusAmountMin;
	}

	public Double getBonusAmountMax() {
		return bonusAmountMax;
	}

	public void setBonusAmountMax(Double bonusAmountMax) {
		this.bonusAmountMax = bonusAmountMax;
	}

	public Double getRechargeAmountMin() {
		return rechargeAmountMin;
	}

	public void setRechargeAmountMin(Double rechargeAmountMin) {
		this.rechargeAmountMin = rechargeAmountMin;
	}

	public Double getRechargeAmountMax() {
		return rechargeAmountMax;
	}

	public void setRechargeAmountMax(Double rechargeAmountMax) {
		this.rechargeAmountMax = rechargeAmountMax;
	}

	public Integer getUsedTokensMin() {
		return usedTokensMin;
	}

	public void setUsedTokensMin(Integer usedTokensMin) {
		this.usedTokensMin = usedTokensMin;
	}

	public Integer getUsedTokensMax() {
		return usedTokensMax;
	}

	public void setUsedTokensMax(Integer usedTokensMax) {
		this.usedTokensMax = usedTokensMax;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getLastUpdateTimeMinStr() {
		return lastUpdateTimeMinStr;
	}

	public void setLastUpdateTimeMinStr(String lastUpdateTimeMinStr) {
		this.lastUpdateTimeMinStr = lastUpdateTimeMinStr;
	}

	public String getLastUpdateTimeMaxStr() {
		return lastUpdateTimeMaxStr;
	}

	public void setLastUpdateTimeMaxStr(String lastUpdateTimeMaxStr) {
		this.lastUpdateTimeMaxStr = lastUpdateTimeMaxStr;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean getIsAesc() {
		return isAesc;
	}

	public void setIsAesc(Boolean isAesc) {
		this.isAesc = isAesc;
	}

	public String getSourceQrCodePk() {
		return sourceQrCodePk;
	}

	public void setSourceQrCodePk(String sourceQrCodePk) {
		this.sourceQrCodePk = sourceQrCodePk;
	}

	public Boolean getIsWarning() {
		return isWarning;
	}

	public void setIsWarning(Boolean isWarning) {
		this.isWarning = isWarning;
	}

	@Override
	public String toString() {
		return "GetAiChatUserListDTO [startPk=" + startPk + ", nickname=" + nickname + ", createTimeMinStr="
				+ createTimeMinStr + ", createTimeMaxStr=" + createTimeMaxStr + ", lastUpdateTimeMinStr="
				+ lastUpdateTimeMinStr + ", lastUpdateTimeMaxStr=" + lastUpdateTimeMaxStr + ", isDelete=" + isDelete
				+ ", isBlock=" + isBlock + ", isWarning=" + isWarning + ", bonusAmountMin=" + bonusAmountMin
				+ ", bonusAmountMax=" + bonusAmountMax + ", rechargeAmountMin=" + rechargeAmountMin
				+ ", rechargeAmountMax=" + rechargeAmountMax + ", usedTokensMin=" + usedTokensMin + ", usedTokensMax="
				+ usedTokensMax + ", sourceQrCodePk=" + sourceQrCodePk + ", limit=" + limit + ", orderBy=" + orderBy
				+ ", isAesc=" + isAesc + "]";
	}

}
