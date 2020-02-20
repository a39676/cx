package demo.base.organizations.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class OrgUserBelongResult extends CommonResultCX {

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
