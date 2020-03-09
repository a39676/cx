package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.OrganzationRolesType;
import demo.base.user.pojo.type.SystemRolesType;

public class InsertAuthDTO {

	private String belongOrgPK;
	private String authName;
	private List<SystemRolesType> sysRoles;
	private List<OrganzationRolesType> orgRoles;

	public String getBelongOrgPK() {
		return belongOrgPK;
	}

	public void setBelongOrgPK(String belongOrgPK) {
		this.belongOrgPK = belongOrgPK;
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
		return "InsertAuthDTO [belongOrgPK=" + belongOrgPK + ", authName=" + authName + ", sysRoles=" + sysRoles
				+ ", orgRoles=" + orgRoles + "]";
	}

}
