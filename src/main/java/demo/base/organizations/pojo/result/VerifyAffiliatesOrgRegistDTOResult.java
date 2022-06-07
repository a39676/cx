package demo.base.organizations.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class VerifyAffiliatesOrgRegistDTOResult extends CommonResult {

	private Long topOrgId;
	private Long belongToOrgId;

	public Long getTopOrgId() {
		return topOrgId;
	}

	public void setTopOrgId(Long topOrgId) {
		this.topOrgId = topOrgId;
	}

	public Long getBelongToOrgId() {
		return belongToOrgId;
	}

	public void setBelongToOrgId(Long belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	@Override
	public String toString() {
		return "VerifyAffiliatesOrgRegistDTOResult [topOrgId=" + topOrgId + ", belongToOrgId=" + belongToOrgId + "]";
	}

}
