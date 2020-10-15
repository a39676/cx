package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.po.AuthRole;
import demo.common.pojo.result.CommonResultCX;

public class FindAuthRoleResult extends CommonResultCX {

	private List<AuthRole> authRoleList;

	public List<AuthRole> getAuthRoleList() {
		return authRoleList;
	}

	public void setAuthRoleList(List<AuthRole> authRoleList) {
		this.authRoleList = authRoleList;
	}

	@Override
	public String toString() {
		return "FindAuthRoleResult [authRoleList=" + authRoleList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
