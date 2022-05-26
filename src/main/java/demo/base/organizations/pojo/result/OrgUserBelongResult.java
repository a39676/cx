package demo.base.organizations.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class OrgUserBelongResult extends CommonResult {

	private boolean isBelong = false;

	public boolean isBelong() {
		return isBelong;
	}

	public void setBelong(boolean isBelong) {
		this.isBelong = isBelong;
	}

	@Override
	public String toString() {
		return "OrgUserBelongResult [isBelong=" + isBelong + "]";
	}

}
