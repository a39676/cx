package demo.base.user.pojo.dto;

public class EditAuthRoleByRoleNameDTO {

	private String authPK;
	private String roleName;

	public String getAuthPK() {
		return authPK;
	}

	public void setAuthPK(String authPK) {
		this.authPK = authPK;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "EditAuthRoleByRoleNameDTO [authPK=" + authPK + ", roleName=" + roleName + "]";
	}

}
