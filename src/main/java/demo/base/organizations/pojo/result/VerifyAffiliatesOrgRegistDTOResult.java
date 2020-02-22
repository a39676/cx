package demo.base.organizations.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class VerifyAffiliatesOrgRegistDTOResult extends CommonResultCX {

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
