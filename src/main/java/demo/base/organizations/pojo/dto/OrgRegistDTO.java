package demo.base.organizations.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrgRegistDTO {

	@Schema(name = "机构名称")
	private String orgName;
	@Schema(name = "直属机构Pk")
	private String belongTo;
	@Schema(name = "顶级机构Pk")
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
