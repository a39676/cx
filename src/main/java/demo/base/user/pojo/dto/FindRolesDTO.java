package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class FindRolesDTO {

	private List<Long> rolesIdList;
	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;
	private List<Long> belongOrgIdList;
	private List<String> roleNameList;

	public List<Long> getRolesIdList() {
		return rolesIdList;
	}

	public void setRolesIdList(List<Long> rolesIdList) {
		this.rolesIdList = rolesIdList;
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

	public List<Long> getBelongOrgIdList() {
		return belongOrgIdList;
	}

	public void setBelongOrgIdList(List<Long> belongOrgIdList) {
		this.belongOrgIdList = belongOrgIdList;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}

	@Override
	public String toString() {
		return "FindRolesDTO [rolesIdList=" + rolesIdList + ", sysRoleTypeList=" + sysRoleTypeList
				+ ", orgRoleTypeList=" + orgRoleTypeList + ", belongOrgIdList=" + belongOrgIdList + ", roleNameList="
				+ roleNameList + "]";
	}

}
