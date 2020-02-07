package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleUserDetail {
    private Long id;

    private Long articleChannelId;

    private Long userId;

    private Boolean connectType;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleChannelId() {
        return articleChannelId;
    }

    public void setArticleChannelId(Long articleChannelId) {
        this.articleChannelId = articleChannelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getConnectType() {
        return connectType;
    }

    public void setConnectType(Boolean connectType) {
        this.connectType = connectType;
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
}