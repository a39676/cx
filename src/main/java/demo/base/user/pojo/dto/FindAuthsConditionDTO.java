package demo.base.user.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class FindAuthsConditionDTO {

	private Integer authType;
	private String authName;
	private boolean authIsDelete = false;
	private boolean authRoleIsDelete = false;
	private boolean roleIsDelete = false;
	private List<Long> authIdList;
	private List<Long> roleIdList;
	private List<String> roleNameList;
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

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public void setRoleIdList(Long roleId) {
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList.add(roleId);
		this.roleIdList = roleIdList;
	}

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
		return "FindAuthsConditionDTO [authType=" + authType + ", authName=" + authName + ", authIsDelete="
				+ authIsDelete + ", authRoleIsDelete=" + authRoleIsDelete + ", roleIsDelete=" + roleIsDelete
				+ ", authIdList=" + authIdList + ", roleIdList=" + roleIdList + ", roleNameList=" + roleNameList
				+ ", belongOrgIdList=" + belongOrgIdList + "]";
	}

}
