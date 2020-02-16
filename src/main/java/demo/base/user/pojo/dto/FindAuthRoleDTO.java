package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

public class FindAuthRoleDTO {

	private List<Long> roleIdList;

	private List<String> roleNameList;
	
	private List<Long> orgIdList;
	
	private List<Long> authIdList;
	
	

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
	}
	
	public void setAuthIdList(Long authId) {
		this.authIdList = Arrays.asList(authId);
	}

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
				+ orgIdList + ", authIdList=" + authIdList + "]";
	}

}
