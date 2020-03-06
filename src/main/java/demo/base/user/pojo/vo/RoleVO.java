package demo.base.user.pojo.vo;

public class RoleVO {

	private String pk;
	private String roleName;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "RoleVO [pk=" + pk + ", roleName=" + roleName + "]";
	}

}
