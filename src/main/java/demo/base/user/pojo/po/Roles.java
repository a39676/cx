package demo.base.user.pojo.po;

public class Roles {

	private Long roleId;
	private String role;
	private Long belongOrg;
	private Boolean isDelete;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role == null ? null : role.trim();
	}

	public Long getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(Long belongOrg) {
		this.belongOrg = belongOrg;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
