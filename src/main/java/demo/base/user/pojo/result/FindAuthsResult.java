package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.po.Auth;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindAuthsResult extends CommonResultCX {

	private List<Auth> authList;

	public List<Auth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<Auth> authList) {
		this.authList = authList;
	}

	@Override
	public String toString() {
		return "FindAuthsResult [authList=" + authList + "]";
	}

}
