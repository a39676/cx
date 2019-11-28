package demo.base.user.pojo.vo;

public class UserIpVO {

	private Long userId;
	private String ip;
	private String forwardIp;
	private String uri;
	private String visitTime;
	private String serverName;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getForwardIp() {
		return forwardIp;
	}

	public void setForwardIp(String forwardIp) {
		this.forwardIp = forwardIp;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	@Override
	public String toString() {
		return "UserIpVO [userId=" + userId + ", ip=" + ip + ", forwardIp=" + forwardIp + ", uri=" + uri
				+ ", visitTime=" + visitTime + ", serverName=" + serverName + "]";
	}

}
