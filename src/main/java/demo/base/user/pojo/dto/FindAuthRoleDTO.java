package demo.base.user.pojo.dto;

import java.util.List;

public class FindAuthRoleDTO {

	private List<Long> roleIdList;

	private List<String> roleNameList;

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}

	@Override
	public String toString() {
		return "FindAuthRoleDTO [roleIdList=" + roleIdList + ", roleNameList=" + roleNameList + "]";
	}

}
