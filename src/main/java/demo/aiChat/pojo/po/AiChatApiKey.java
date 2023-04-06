package demo.aiChat.pojo.po;

import java.time.LocalDateTime;

public class AiChatApiKey {
    private Long apiKeyDecrypt;

    private Long aiChatUserId;

    private LocalDateTime createTime;

    private LocalDateTime lastUsedTime;

    private Boolean isDelete;

    public Long getApiKeyDecrypt() {
        return apiKeyDecrypt;
    }

    public void setApiKeyDecrypt(Long apiKeyDecrypt) {
        this.apiKeyDecrypt = apiKeyDecrypt;
    }

    public Long getAiChatUserId() {
        return aiChatUserId;
    }

    public void setAiChatUserId(Long aiChatUserId) {
        this.aiChatUserId = aiChatUserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(LocalDateTime lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}