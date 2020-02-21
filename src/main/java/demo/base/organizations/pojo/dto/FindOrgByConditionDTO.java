package demo.base.organizations.pojo.dto;

public class FindOrgByConditionDTO {

	private String orgName;
	private String orgPk;
	private Long orgId;
	private Long belongTo;
	private Long topOrg;
	private String creatorName;
	private String creatorPk;
	private Boolean isDelete = false;

	public String getCreatorPk() {
		return creatorPk;
	}

	public void setCreatorPk(String creatorPk) {
		this.creatorPk = creatorPk;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getOrgPk() {
		return orgPk;
	}

	public void setOrgPk(String orgPk) {
		this.orgPk = orgPk;
	}

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

	@Override
	public String toString() {
		return "FindOrgByConditionDTO [orgName=" + orgName + ", orgPk=" + orgPk + ", orgId=" + orgId + ", belongTo="
				+ belongTo + ", topOrg=" + topOrg + ", creatorName=" + creatorName + ", creatorPk=" + creatorPk
				+ ", isDelete=" + isDelete + "]";
	}

}
