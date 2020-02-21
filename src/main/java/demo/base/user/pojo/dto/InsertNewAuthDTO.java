package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class InsertNewAuthDTO {

	private Long belongOrgId;
	private String authName;
	private List<RolesType> roles;

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

	public List<RolesType> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesType> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "InsertNewAuthDTO [belongOrgId=" + belongOrgId + ", authName=" + authName + ", roles=" + roles + "]";
	}

}
