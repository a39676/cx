package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class FindRolesDTO {

	private List<Long> authIdList;
	private List<Long> rolesIdList;
	private List<RolesType> roleTypeList;
	private List<Long> belongOrgIdList;

	public List<Long> getBelongOrgIdList() {
		return belongOrgIdList;
	}

	public void setBelongOrgIdList(List<Long> belongOrgIdList) {
		this.belongOrgIdList = belongOrgIdList;
	}

	public List<RolesType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RolesType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	@Override
	public String toString() {
		return "FindRolesDTO [authIdList=" + authIdList + ", rolesIdList=" + rolesIdList + ", roleTypeList="
				+ roleTypeList + ", belongOrgIdList=" + belongOrgIdList + "]";
	}

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
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

}
