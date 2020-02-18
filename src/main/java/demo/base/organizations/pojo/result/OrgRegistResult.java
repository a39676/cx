package demo.base.organizations.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.organizations.pojo.po.Organizations;

public class OrgRegistResult extends CommonResult {

	private Organizations baseOrg;

	public Organizations getBaseOrg() {
		return baseOrg;
	}

	public void setBaseOrg(Organizations baseOrg) {
		this.baseOrg = baseOrg;
	}

	@Override
	public String toString() {
		return "OrgRegistResult [baseOrg=" + baseOrg + "]";
	}

}
