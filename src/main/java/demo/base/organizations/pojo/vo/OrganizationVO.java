package demo.base.organizations.pojo.vo;

public class OrganizationVO {

	private String pk;

	private String orgName;

	private String belongToPk;

	private String belongToOrgName;

	private String topOrgPk;

	private String topOrgName;

	private Boolean isDelete;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getBelongToPk() {
		return belongToPk;
	}

	public void setBelongToPk(String belongToPk) {
		this.belongToPk = belongToPk;
	}

	public String getBelongToOrgName() {
		return belongToOrgName;
	}

	public void setBelongToOrgName(String belongToOrgName) {
		this.belongToOrgName = belongToOrgName;
	}

	public String getTopOrgPk() {
		return topOrgPk;
	}

	public void setTopOrgPk(String topOrgPk) {
		this.topOrgPk = topOrgPk;
	}

	public String getTopOrgName() {
		return topOrgName;
	}

	public void setTopOrgName(String topOrgName) {
		this.topOrgName = topOrgName;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "OrganizationVO [pk=" + pk + ", orgName=" + orgName + ", belongToPk=" + belongToPk + ", belongToOrgName="
				+ belongToOrgName + ", topOrgPk=" + topOrgPk + ", topOrgName=" + topOrgName + ", isDelete=" + isDelete
				+ "]";
	}

}
