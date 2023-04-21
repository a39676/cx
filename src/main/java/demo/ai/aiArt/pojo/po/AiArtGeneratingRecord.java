package demo.ai.aiArt.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AiArtGeneratingRecord {
    private Long id;

    private Long aiUserId;

    private BigDecimal tokens;

    private LocalDateTime createTime;

    private Boolean isDelete;

    private Boolean isWarning;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAiUserId() {
        return aiUserId;
    }

    public void setAiUserId(Long aiUserId) {
        this.aiUserId = aiUserId;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal tokens) {
        this.tokens = tokens;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(Boolean isWarning) {
        this.isWarning = isWarning;
    }
}