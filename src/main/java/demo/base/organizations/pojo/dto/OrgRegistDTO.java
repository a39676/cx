package demo.base.organizations.pojo.dto;

public class OrgRegistDTO {

	private String orgName;
	private String belongTo;
	private String topOrg;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
		return "OrgRegistDTO [orgName=" + orgName + ", belongTo=" + belongTo + ", topOrg=" + topOrg + "]";
	}

}
