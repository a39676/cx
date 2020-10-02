package demo.base.user.pojo.result;

import demo.common.pojo.result.CommonResultCX;

public class InsertNewAuthResult extends CommonResultCX {

	private String authPK;

	public String getAuthPK() {
		return authPK;
	}

	public void setAuthPK(String authPK) {
		this.authPK = authPK;
	}

	@Override
	public String toString() {
		return "InsertNewAuthResult [authPK=" + authPK + "]";
	}

}
