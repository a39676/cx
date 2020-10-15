package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.UserAuth;
import demo.common.pojo.result.CommonResultCX;

public class FindUserAuthResult extends CommonResultCX {

	private List<UserAuth> userAuthList;

	private List<Auth> authList;

	public List<UserAuth> getUserAuthList() {
		return userAuthList;
	}

	public void setUserAuthList(List<UserAuth> userAuthList) {
		this.userAuthList = userAuthList;
	}

	public List<Auth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<Auth> authList) {
		this.authList = authList;
	}

	@Override
	public String toString() {
		return "FindUserAuthResult [userAuthList=" + userAuthList + ", authList=" + authList + "]";
	}

}
