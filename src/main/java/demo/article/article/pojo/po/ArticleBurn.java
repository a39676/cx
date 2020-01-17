package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleBurn {
    private Long articleId;

    private Integer readCount;

    private Integer readLimit;

    private LocalDateTime validTime;

    private Long readKey;

    private Long burnKey;

    private Boolean isBurned;

    private LocalDateTime createTime;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getReadLimit() {
        return readLimit;
    }

    public void setReadLimit(Integer readLimit) {
        this.readLimit = readLimit;
    }

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    public Long getReadKey() {
        return readKey;
    }

    public void setReadKey(Long readKey) {
        this.readKey = readKey;
    }

    public Long getBurnKey() {
        return burnKey;
    }

    public void setBurnKey(Long burnKey) {
        this.burnKey = burnKey;
    }

    public Boolean getIsBurned() {
        return isBurned;
    }

    public void setIsBurned(Boolean isBurned) {
        this.isBurned = isBurned;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}