package demo.base.organizations.pojo.bo;

public class FindOrgByConditionBO {

	private String orgName;
	private Long orgId;
	private Long belongTo;
	private Long topOrg;
	private String creatorName;
	private Long creatorId;
	private Boolean isDelete = false;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Long belongTo) {
		this.belongTo = belongTo;
	}

	public Long getTopOrg() {
		return topOrg;
	}

	public void setTopOrg(Long topOrg) {
		this.topOrg = topOrg;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "FindOrgByConditionBO [orgName=" + orgName + ", orgId=" + orgId + ", belongTo=" + belongTo + ", topOrg="
				+ topOrg + ", creatorName=" + creatorName + ", creatorId=" + creatorId + ", isDelete=" + isDelete + "]";
	}

}
