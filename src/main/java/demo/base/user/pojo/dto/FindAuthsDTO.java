package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class FindAuthsDTO {

	private Integer authType;
	private String authName;
	private boolean authIsDelete = false;
	private boolean authRoleIsDelete = false;
	private boolean roleIsDelete = false;
	private List<Long> authIdList;
	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;
	private List<Long> belongOrgIdList;

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public boolean isAuthIsDelete() {
		return authIsDelete;
	}

	public void setAuthIsDelete(boolean authIsDelete) {
		this.authIsDelete = authIsDelete;
	}

	public boolean isAuthRoleIsDelete() {
		return authRoleIsDelete;
	}

	public void setAuthRoleIsDelete(boolean authRoleIsDelete) {
		this.authRoleIsDelete = authRoleIsDelete;
	}

	public boolean isRoleIsDelete() {
		return roleIsDelete;
	}

	public void setRoleIsDelete(boolean roleIsDelete) {
		this.roleIsDelete = roleIsDelete;
	}

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
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

	@Override
	public String toString() {
		return "FindAuthsDTO [authType=" + authType + ", authName=" + authName + ", authIsDelete=" + authIsDelete
				+ ", authRoleIsDelete=" + authRoleIsDelete + ", roleIsDelete=" + roleIsDelete + ", authIdList="
				+ authIdList + ", sysRoleTypeList=" + sysRoleTypeList + ", orgRoleTypeList=" + orgRoleTypeList
				+ ", belongOrgIdList=" + belongOrgIdList + "]";
	}

}
