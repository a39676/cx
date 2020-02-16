package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

public class FindRolesDTO {

	private List<Long> rolesIdList;
	
	private List<String> roleNameList;
	
	private List<Long> belongOrgIdList;
	
	private List<Long> authIdList;
	
	@Override
	public String toString() {
		return "FindRolesDTO [rolesIdList=" + rolesIdList + ", roleNameList=" + roleNameList + ", belongOrgIdList="
				+ belongOrgIdList + ", authIdList=" + authIdList + "]";
	}

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
	}
	
	public void setAuthIdList(Long authId) {
		this.authIdList = Arrays.asList(authId);
	}

	public void setRolesIdList(Long roleId) {
		this.rolesIdList = Arrays.asList(roleId);
	}

	public List<Long> getRolesIdList() {
		return rolesIdList;
	}

	public void setRolesIdList(List<Long> rolesIdList) {
		this.rolesIdList = rolesIdList;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}
	
	public void setRoleNameList(String roleName) {
		this.roleNameList = Arrays.asList(roleName);
	}

	public List<Long> getBelongOrgIdList() {
		return belongOrgIdList;
	}

	public void setBelongOrgIdList(List<Long> belongOrgIdList) {
		this.belongOrgIdList = belongOrgIdList;
	}
	
	public void setBelongOrgIdList(Long belongOrgId) {
		this.belongOrgIdList = Arrays.asList(belongOrgId);
	}
}
