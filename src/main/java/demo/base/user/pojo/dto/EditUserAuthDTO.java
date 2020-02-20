package demo.base.user.pojo.dto;

public class EditUserAuthDTO {

	private String userPk;

	private String newAuthPk;

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getNewAuthPk() {
		return newAuthPk;
	}

	public void setNewAuthPk(String newAuthPk) {
		this.newAuthPk = newAuthPk;
	}

	@Override
	public String toString() {
		return "InsertUserAuthDTO [userPk=" + userPk + ", newAuthPk=" + newAuthPk + "]";
	}

}
