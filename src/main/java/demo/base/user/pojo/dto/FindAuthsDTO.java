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
	private List<String> authPkList;
	private List<SystemRolesType> sysRoleTypeList;
	private List<OrganzationRolesType> orgRoleTypeList;
	private List<String> belongOrgPkList;
	private boolean isDelete = false;

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

	public boolean getAuthIsDelete() {
		return authIsDelete;
	}

	public void setAuthIsDelete(boolean authIsDelete) {
		this.authIsDelete = authIsDelete;
	}

	public boolean getAuthRoleIsDelete() {
		return authRoleIsDelete;
	}

	public void setAuthRoleIsDelete(boolean authRoleIsDelete) {
		this.authRoleIsDelete = authRoleIsDelete;
	}

	public boolean getRoleIsDelete() {
		return roleIsDelete;
	}

	public void setRoleIsDelete(boolean roleIsDelete) {
		this.roleIsDelete = roleIsDelete;
	}

	public List<String> getAuthPkList() {
		return authPkList;
	}

	public void setAuthPkList(List<String> authPkList) {
		this.authPkList = authPkList;
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

	public List<String> getBelongOrgPkList() {
		return belongOrgPkList;
	}

	public void setBelongOrgPkList(List<String> belongOrgPkList) {
		this.belongOrgPkList = belongOrgPkList;
	}

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "FindAuthsDTO [authType=" + authType + ", authName=" + authName + ", authIsDelete=" + authIsDelete
				+ ", authRoleIsDelete=" + authRoleIsDelete + ", roleIsDelete=" + roleIsDelete + ", authPkList="
				+ authPkList + ", sysRoleTypeList=" + sysRoleTypeList + ", orgRoleTypeList=" + orgRoleTypeList
				+ ", belongOrgPkList=" + belongOrgPkList + ", isDelete=" + isDelete + "]";
	}

}
