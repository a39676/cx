package demo.base.user.pojo.po;

import java.time.LocalDateTime;

public class UserIp {
    private Long userId;

	private Long ip;

	private Long forwardIp;

	private String serverName;

	private String uri;

	private LocalDateTime createTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIp() {
		return ip;
	}

	public void setIp(Long ip) {
		this.ip = ip;
	}

	public Long getForwardIp() {
		return forwardIp;
	}

	public void setForwardIp(Long forwardIp) {
		this.forwardIp = forwardIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName == null ? null : serverName.trim();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri == null ? null : uri.trim();
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

}