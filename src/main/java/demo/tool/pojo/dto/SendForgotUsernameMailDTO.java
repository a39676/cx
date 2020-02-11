package demo.tool.pojo.dto;

public class SendForgotUsernameMailDTO {

	private Long userId;
	private String userName;
	private String email;
	private String hostName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public String toString() {
		return "SendForgotUsernameMailDTO [userId=" + userId + ", userName=" + userName + ", email=" + email
				+ ", hostName=" + hostName + "]";
	}

}
