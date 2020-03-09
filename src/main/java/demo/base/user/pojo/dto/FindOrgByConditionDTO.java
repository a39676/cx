package demo.base.user.pojo.dto;

public class FindOrgByConditionDTO {

	private String orgName;
	private String orgPk;
	private String belongTo;
	private String topOrg;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getTopOrg() {
		return topOrg;
	}

	public void setTopOrg(String topOrg) {
		this.topOrg = topOrg;
	}

	@Override
	public String toString() {
		return "FindOrgByConditionDTO [orgName=" + orgName + ", orgPk=" + orgPk + ", belongTo=" + belongTo + ", topOrg="
				+ topOrg + ", creatorName=" + creatorName + ", creatorPk=" + creatorPk + ", isDelete=" + isDelete + "]";
	}

}
