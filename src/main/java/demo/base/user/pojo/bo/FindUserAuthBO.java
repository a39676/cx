package demo.base.user.pojo.bo;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class FindUserAuthBO {

	private Long userId;
	private Long authId;
	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
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
		return "FindUserAuthBO [userId=" + userId + ", authId=" + authId + ", sysRoleTypeList=" + sysRoleTypeList
				+ ", orgRoleTypeList=" + orgRoleTypeList + "]";
	}

}
