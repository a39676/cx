package demo.tool.mail.pojo.po;

import java.time.LocalDateTime;

public class MailRecord {
    private Long id;

    private Long userId;

    private LocalDateTime validTime;

    private Integer mailType;

    private Boolean wasUsed;

    private LocalDateTime createTime;

    private LocalDateTime usedTime;

    private Integer sendCount;

    private LocalDateTime resendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    public Integer getMailType() {
        return mailType;
    }

    public void setMailType(Integer mailType) {
        this.mailType = mailType;
    }

    public Boolean getWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(Boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public LocalDateTime getResendTime() {
        return resendTime;
    }

    public void setResendTime(LocalDateTime resendTime) {
        this.resendTime = resendTime;
    }
}