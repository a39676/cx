package demo.base.user.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class FindAuthsDTO {

	private Integer authType;
	private String authName;
	private boolean authIsDelete = false;
	private boolean authRoleIsDelete = false;
	private boolean roleIsDelete = false;
	private List<Long> authIdList;
	private List<RolesType> roleTypeList;
	private List<Long> belongOrgIdList;

	public boolean isAuthRoleIsDelete() {
		return authRoleIsDelete;
	}

	public void setAuthRoleIsDelete(boolean authRoleIsDelete) {
		this.authRoleIsDelete = authRoleIsDelete;
	}

	public boolean isAuthIsDelete() {
		return authIsDelete;
	}

	public void setAuthIsDelete(boolean authIsDelete) {
		this.authIsDelete = authIsDelete;
	}

	public boolean isRoleIsDelete() {
		return roleIsDelete;
	}

	public void setRoleIsDelete(boolean roleIsDelete) {
		this.roleIsDelete = roleIsDelete;
	}

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

	public List<RolesType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RolesType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
	}

	public List<Long> getBelongOrgIdList() {
		return belongOrgIdList;
	}

	public void setBelongOrgIdList(List<Long> belongOrgIdList) {
		this.belongOrgIdList = belongOrgIdList;
	}

	public void setBelongOrgIdList(Long belongOrgId) {
		List<Long> belongOrgIdList = new ArrayList<Long>();
		belongOrgIdList.add(belongOrgId);
		this.belongOrgIdList = belongOrgIdList;
	}

	@Override
	public String toString() {
		return "FindAuthsDTO [authType=" + authType + ", authName=" + authName + ", authIsDelete=" + authIsDelete
				+ ", authRoleIsDelete=" + authRoleIsDelete + ", roleIsDelete=" + roleIsDelete + ", authIdList="
				+ authIdList + ", roleTypeList=" + roleTypeList + ", belongOrgIdList=" + belongOrgIdList + "]";
	}

}
