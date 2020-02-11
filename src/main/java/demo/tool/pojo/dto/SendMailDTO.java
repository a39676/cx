package demo.tool.pojo.dto;

public class SendMailDTO {

	private String hostName;
	private String sendTo;
	private String nickName;
	private Long userId;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "SendMailDTO [hostName=" + hostName + ", sendTo=" + sendTo + ", nickName=" + nickName + ", userId="
				+ userId + "]";
	}

}
