package demo.base.user.pojo.po;

import java.time.LocalDateTime;

public class UserIp {
    private Long userId;

    private String ip;

    private String forwardIp;

    private String serverName;

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
        this.ip = ip == null ? null : ip.trim();
    }

    public String getForwardIp() {
        return forwardIp;
    }

    public void setForwardIp(String forwardIp) {
        this.forwardIp = forwardIp == null ? null : forwardIp.trim();
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