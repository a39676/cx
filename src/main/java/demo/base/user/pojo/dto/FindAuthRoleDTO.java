package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class FindAuthRoleDTO {

	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;
	private List<Long> orgIdList;
	private List<Long> authIdList;
	private List<String> roleNameList;

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
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

	public List<Long> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Long> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public void setOrgIdList(Long orgId) {
		this.orgIdList = Arrays.asList(orgId);
	}

	public List<SystemRolesType> getSysRoleTypeList() {
		return sysRoleTypeList;
	}

	public void setSysRoleTypeList(List<SystemRolesType> sysRoleTypeList) {
		this.sysRoleTypeList = sysRoleTypeList;
	}

	public List<OrganzationRolesType> getOrgRoleTypeList() {
		return orgRoleTypeList;
	}

	public void setOrgRoleTypeList(List<OrganzationRolesType> orgRoleTypeList) {
		this.orgRoleTypeList = orgRoleTypeList;
	}

	@Override
	public String toString() {
		return "FindAuthRoleDTO [sysRoleTypeList=" + sysRoleTypeList + ", orgRoleTypeList=" + orgRoleTypeList
				+ ", orgIdList=" + orgIdList + ", authIdList=" + authIdList + ", roleNameList=" + roleNameList + "]";
	}

}
