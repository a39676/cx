package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleLongSummary {
    private Long articleId;

    private Long userId;

    private String articleTitle;

    private String filePath;

    private LocalDateTime createTime;

    private Boolean isHot;

    private Integer hotLevel;

    private LocalDateTime hotValidTime;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Integer getHotLevel() {
        return hotLevel;
    }

    public void setHotLevel(Integer hotLevel) {
        this.hotLevel = hotLevel;
    }

    public LocalDateTime getHotValidTime() {
        return hotValidTime;
    }

    public void setHotValidTime(LocalDateTime hotValidTime) {
        this.hotValidTime = hotValidTime;
    }
}