package demo.article.articleComment.pojo.po;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;

    private Long userId;

    private String tmpNickName;

    private String tmpEmail;

    private Long articleId;

    private String path;

    private LocalDateTime createTime;

    private Long replyOf;

    private Boolean isDelete;

    private Boolean isPass;

    private Boolean isReject;

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

    public String getTmpNickName() {
        return tmpNickName;
    }

    public void setTmpNickName(String tmpNickName) {
        this.tmpNickName = tmpNickName == null ? null : tmpNickName.trim();
    }

    public String getTmpEmail() {
        return tmpEmail;
    }

    public void setTmpEmail(String tmpEmail) {
        this.tmpEmail = tmpEmail == null ? null : tmpEmail.trim();
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getReplyOf() {
        return replyOf;
    }

    public void setReplyOf(Long replyOf) {
        this.replyOf = replyOf;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public Boolean getIsReject() {
        return isReject;
    }

    public void setIsReject(Boolean isReject) {
        this.isReject = isReject;
    }
}