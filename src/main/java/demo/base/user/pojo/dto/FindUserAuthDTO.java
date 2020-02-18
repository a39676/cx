package demo.base.user.pojo.dto;

public class FindUserAuthDTO {

	private Long userId;

	private Long authId;

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FindUserAuthDTO [userId=" + userId + ", authId=" + authId + "]";
	}

}
