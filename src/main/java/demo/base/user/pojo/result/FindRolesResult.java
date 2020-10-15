package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.po.Roles;
import demo.common.pojo.result.CommonResultCX;

public class FindRolesResult extends CommonResultCX {

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
