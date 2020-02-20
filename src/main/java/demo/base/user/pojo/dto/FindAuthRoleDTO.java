package demo.base.user.pojo.dto;

import java.util.Arrays;
import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class FindAuthRoleDTO {

	private List<RolesType> roleTypeList;

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

	public List<RolesType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RolesType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	@Override
	public String toString() {
		return "FindAuthRoleDTO [roleTypeList=" + roleTypeList + ", orgIdList=" + orgIdList + ", authIdList="
				+ authIdList + "]";
	}

}
