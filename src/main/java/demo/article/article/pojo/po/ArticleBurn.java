package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleBurn {
    private Long articleId;

    private Integer readCount;

    private Integer readLimit;

    private LocalDateTime validTime;

    private Long readId;

    private Long burnId;

    private String filePath;

    private Boolean isBurned;

    private LocalDateTime createTime;

    private String md5hash;

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

    public Long getReadId() {
        return readId;
    }

    public void setReadId(Long readId) {
        this.readId = readId;
    }

    public Long getBurnId() {
        return burnId;
    }

    public void setBurnId(Long burnId) {
        this.burnId = burnId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
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

    public String getMd5hash() {
        return md5hash;
    }

    public void setMd5hash(String md5hash) {
        this.md5hash = md5hash == null ? null : md5hash.trim();
    }
}