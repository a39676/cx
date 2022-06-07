package demo.base.user.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.po.Roles;

public class FindRolesResult extends CommonResult {

	private List<Roles> roleList;

	public List<Roles> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Roles> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "FindRolesResult [roleList=" + roleList + "]";
	}

}
