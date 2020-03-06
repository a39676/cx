package demo.base.user.pojo.dto;

public class EditAuthRoleDTO {

	private String authPK;
	private String rolePK;

	public String getAuthPK() {
		return authPK;
	}

	public void setAuthPK(String authPK) {
		this.authPK = authPK;
	}

	public String getRolePK() {
		return rolePK;
	}

	public void setRolePK(String rolePK) {
		this.rolePK = rolePK;
	}

	@Override
	public String toString() {
		return "EditAuthRoleDTO [authPK=" + authPK + ", rolePK=" + rolePK + "]";
	}

}
