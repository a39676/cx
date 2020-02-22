package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class FindUserAuthDTO {

	private String userPk;
	private String authPk;
	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getAuthPk() {
		return authPk;
	}

	public void setAuthPk(String authPk) {
		this.authPk = authPk;
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
		return "FindUserAuthDTO [userPk=" + userPk + ", authPk=" + authPk + ", sysRoleTypeList=" + sysRoleTypeList
				+ ", orgRoleTypeList=" + orgRoleTypeList + "]";
	}

}
