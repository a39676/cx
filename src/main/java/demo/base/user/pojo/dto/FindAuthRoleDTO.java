package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

public class FindAuthRoleDTO {

	private List<Long> roleIdList;

	private List<String> roleNameList;
	
	private List<Long> orgIdList;

	public List<Long> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Long> orgIdList) {
		this.orgIdList = orgIdList;
	}
	
	public void setOrgIdList(Long orgId) {
		this.orgIdList = Arrays.asList(orgId);
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
	
	public void setRoleIdList(Long roleId) {
		this.roleIdList = Arrays.asList(roleId);
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}

	@Override
	public String toString() {
		return "FindAuthRoleDTO [roleIdList=" + roleIdList + ", roleNameList=" + roleNameList + ", orgIdList="
				+ orgIdList + "]";
	}

}
