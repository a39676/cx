package demo.ai.aiArt.pojo.dto;

public class AiUserDetailInJsonDTO {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AiUserDetailInJsonDTO [userId=" + userId + "]";
	}

}
