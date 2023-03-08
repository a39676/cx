package demo.aiChat.pojo.po;

import java.time.LocalDateTime;

public class AiChatUserChatHistory {
    private Long userId;

    private String historyFilePath;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDelete;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHistoryFilePath() {
        return historyFilePath;
    }

    public void setHistoryFilePath(String historyFilePath) {
        this.historyFilePath = historyFilePath == null ? null : historyFilePath.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}