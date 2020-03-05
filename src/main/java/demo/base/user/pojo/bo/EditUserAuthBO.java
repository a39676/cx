package demo.base.user.pojo.bo;

public class EditUserAuthBO {

	private Long userId;

	private Long newAuthId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAuthId() {
		return newAuthId;
	}

	public void setAuthId(Long newAuthId) {
		this.newAuthId = newAuthId;
	}

	@Override
	public String toString() {
		return "EditUserAuthBO [userId=" + userId + ", newAuthId=" + newAuthId + "]";
	}

}
