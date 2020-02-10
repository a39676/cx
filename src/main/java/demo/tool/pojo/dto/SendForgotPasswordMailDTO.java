package demo.tool.pojo.dto;

public class SendForgotPasswordMailDTO {

	private Long userId;
	private String sendTo;
	private String hostName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Override
	public String toString() {
		return "SendForgotPasswordMailDTO [userId=" + userId + ", sendTo=" + sendTo + ", hostName=" + hostName + "]";
	}

}
