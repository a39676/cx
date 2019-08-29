package demo.organizations.pojo.dto;

import io.swagger.annotations.ApiModelProperty;

public class OrgRegistDTO {

	@ApiModelProperty(value = "机构名称")
	private String orgName;
	@ApiModelProperty(value = "直属机构id")
	private Long belongTo;
	@ApiModelProperty(value = "顶级机构id")
	private Long topOrg;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	@Override
	public String toString() {
		return "OrgRegistDTO [orgName=" + orgName + ", belongTo=" + belongTo + ", topOrg=" + topOrg + "]";
	}

}
