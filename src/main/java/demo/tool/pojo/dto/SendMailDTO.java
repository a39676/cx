package demo.tool.pojo.dto;

public class SendMailDTO {

	private String hostName;
	private Long userId;
	private String sendTo;
	private String nickName;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "SendRegistMailDTO [hostName=" + hostName + ", userId=" + userId + ", sendTo=" + sendTo + ", nickName="
				+ nickName + "]";
	}

}
