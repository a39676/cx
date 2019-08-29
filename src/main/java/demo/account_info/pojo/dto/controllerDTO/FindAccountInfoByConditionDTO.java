package demo.account_info.pojo.dto.controllerDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FindAccountInfoByConditionDTO {

	private Long accountId;
	private List<Long> accountIdList;
	private Long accountHolderId;
	private String accountNumber;
	private String accountAlias;
	private Integer accountType;
	private Long accountAffiliation;
	private LocalDateTime vaildStartDate;
	private LocalDateTime vaildEndDate;
	private Long bankId;
	private Long bankUnionId;
	private BigDecimal minAccountBalance;
	private BigDecimal maxAccountBalance;
	private BigDecimal minCreditsQuota;
	private BigDecimal maxCreditsQuota;
	private BigDecimal minTemproraryCreditsQuota;
	private BigDecimal maxTemproraryCreditsQuota;
	private LocalDateTime minTemproraryCreditsVaildDate;
	private LocalDateTime maxTemproraryCreditsVaildDate;
	private String remark;
	private Boolean isDelete = false;
	private LocalDateTime createStartTime;
	private LocalDateTime createEndTime;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<Long> getAccountIdList() {
		return accountIdList;
	}

	public void setAccountIdList(List<Long> accountIdList) {
		this.accountIdList = accountIdList;
	}

	public Long getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(Long accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountAlias() {
		return accountAlias;
	}

	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Long getAccountAffiliation() {
		return accountAffiliation;
	}

	public void setAccountAffiliation(Long accountAffiliation) {
		this.accountAffiliation = accountAffiliation;
	}

	public LocalDateTime getVaildStartDate() {
		return vaildStartDate;
	}

	public void setVaildStartDate(LocalDateTime vaildStartDate) {
		this.vaildStartDate = vaildStartDate;
	}

	public LocalDateTime getVaildEndDate() {
		return vaildEndDate;
	}

	public void setVaildEndDate(LocalDateTime vaildEndDate) {
		this.vaildEndDate = vaildEndDate;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public BigDecimal getMinAccountBalance() {
		return minAccountBalance;
	}

	public void setMinAccountBalance(BigDecimal minAccountBalance) {
		this.minAccountBalance = minAccountBalance;
	}

	public BigDecimal getMaxAccountBalance() {
		return maxAccountBalance;
	}

	public void setMaxAccountBalance(BigDecimal maxAccountBalance) {
		this.maxAccountBalance = maxAccountBalance;
	}

	public BigDecimal getMinCreditsQuota() {
		return minCreditsQuota;
	}

	public void setMinCreditsQuota(BigDecimal minCreditsQuota) {
		this.minCreditsQuota = minCreditsQuota;
	}

	public BigDecimal getMaxCreditsQuota() {
		return maxCreditsQuota;
	}

	public void setMaxCreditsQuota(BigDecimal maxCreditsQuota) {
		this.maxCreditsQuota = maxCreditsQuota;
	}

	public BigDecimal getMinTemproraryCreditsQuota() {
		return minTemproraryCreditsQuota;
	}

	public void setMinTemproraryCreditsQuota(BigDecimal minTemproraryCreditsQuota) {
		this.minTemproraryCreditsQuota = minTemproraryCreditsQuota;
	}

	public BigDecimal getMaxTemproraryCreditsQuota() {
		return maxTemproraryCreditsQuota;
	}

	public void setMaxTemproraryCreditsQuota(BigDecimal maxTemproraryCreditsQuota) {
		this.maxTemproraryCreditsQuota = maxTemproraryCreditsQuota;
	}

	public LocalDateTime getMinTemproraryCreditsVaildDate() {
		return minTemproraryCreditsVaildDate;
	}

	public void setMinTemproraryCreditsVaildDate(LocalDateTime minTemproraryCreditsVaildDate) {
		this.minTemproraryCreditsVaildDate = minTemproraryCreditsVaildDate;
	}

	public LocalDateTime getMaxTemproraryCreditsVaildDate() {
		return maxTemproraryCreditsVaildDate;
	}

	public void setMaxTemproraryCreditsVaildDate(LocalDateTime maxTemproraryCreditsVaildDate) {
		this.maxTemproraryCreditsVaildDate = maxTemproraryCreditsVaildDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public LocalDateTime getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(LocalDateTime createStartTime) {
		this.createStartTime = createStartTime;
	}

	public LocalDateTime getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(LocalDateTime createEndTime) {
		this.createEndTime = createEndTime;
	}

	@Override
	public String toString() {
		return "FindByConditionDTO [accountId=" + accountId + ", accountIdList=" + accountIdList + ", accountHolderId="
				+ accountHolderId + ", accountNumber=" + accountNumber + ", accountAlias=" + accountAlias
				+ ", accountType=" + accountType + ", accountAffiliation=" + accountAffiliation + ", vaildStartDate="
				+ vaildStartDate + ", vaildEndDate=" + vaildEndDate + ", bankId=" + bankId + ", bankUnionId="
				+ bankUnionId + ", minAccountBalance=" + minAccountBalance + ", maxAccountBalance=" + maxAccountBalance
				+ ", minCreditsQuota=" + minCreditsQuota + ", maxCreditsQuota=" + maxCreditsQuota
				+ ", minTemproraryCreditsQuota=" + minTemproraryCreditsQuota + ", maxTemproraryCreditsQuota="
				+ maxTemproraryCreditsQuota + ", minTemproraryCreditsVaildDate=" + minTemproraryCreditsVaildDate
				+ ", maxTemproraryCreditsVaildDate=" + maxTemproraryCreditsVaildDate + ", remark=" + remark
				+ ", isDelete=" + isDelete + ", createStartTime=" + createStartTime + ", createEndTime=" + createEndTime
				+ "]";
	}

}
