package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class InsertAuthDTO {

	private Long belongOrgId;
	private String authName;
	private List<SystemRolesType> sysRoles;
	private List<OrganzationRolesType> orgRoles;

	public Long getBelongOrgId() {
		return belongOrgId;
	}

	public void setBelongOrgId(Long belongOrgId) {
		this.belongOrgId = belongOrgId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public List<SystemRolesType> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SystemRolesType> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public List<OrganzationRolesType> getOrgRoles() {
		return orgRoles;
	}

	public void setOrgRoles(List<OrganzationRolesType> orgRoles) {
		this.orgRoles = orgRoles;
	}

	@Override
	public String toString() {
		return "InsertNewAuthDTO [belongOrgId=" + belongOrgId + ", authName=" + authName + ", sysRoles=" + sysRoles
				+ ", orgRoles=" + orgRoles + "]";
	}

}
