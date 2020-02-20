package demo.base.user.pojo.vo;

public class UserAuthVO {

	private String pk;

	private String userPk;

	private String authPk;

	private Boolean isDelete;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getAuthPk() {
		return authPk;
	}

	public void setAuthPk(String authPk) {
		this.authPk = authPk;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "UserAuthVO [pk=" + pk + ", userPk=" + userPk + ", authPk=" + authPk + ", isDelete=" + isDelete + "]";
	}

}
