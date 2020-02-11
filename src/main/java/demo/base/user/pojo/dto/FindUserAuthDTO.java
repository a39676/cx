package demo.base.user.pojo.dto;

public class FindUserAuthDTO {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FindUserAuthDTO [userId=" + userId + "]";
	}

}
