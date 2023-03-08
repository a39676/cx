package demo.aiChat.pojo.po;

import java.time.LocalDateTime;

public class AiChatUserMembership extends AiChatUserMembershipKey {
    private Integer membershipVersion;

    private LocalDateTime createTime;

    private LocalDateTime expiredTime;

    private Boolean isDelete;

    public Integer getMembershipVersion() {
        return membershipVersion;
    }

    public void setMembershipVersion(Integer membershipVersion) {
        this.membershipVersion = membershipVersion;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}