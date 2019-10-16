package demo.base.user.pojo.vo;

import java.time.LocalDateTime;

public class UserIpVO {

	private Long userId;

	private String ip;

	private String forwardIp;

	private String uri;

	private LocalDateTime createTime;

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

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UserIpVO [userId=" + userId + ", ip=" + ip + ", forwardIp=" + forwardIp + ", uri=" + uri
				+ ", createTime=" + createTime + "]";
	}

}
