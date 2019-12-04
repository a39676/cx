package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleLongFeedback {
    private Long id;

    private Long articleId;

    private Long articleCreatorId;

    private Long feedbackUserId;

    private String feedbackUserNickname;

    private String articleTitle;

    private String email;

    private String mobile;

    private Integer feedbackType;

    private String feedback;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleCreatorId() {
        return articleCreatorId;
    }

    public void setArticleCreatorId(Long articleCreatorId) {
        this.articleCreatorId = articleCreatorId;
    }

    public Long getFeedbackUserId() {
        return feedbackUserId;
    }

    public void setFeedbackUserId(Long feedbackUserId) {
        this.feedbackUserId = feedbackUserId;
    }

    public String getFeedbackUserNickname() {
        return feedbackUserNickname;
    }

    public void setFeedbackUserNickname(String feedbackUserNickname) {
        this.feedbackUserNickname = feedbackUserNickname == null ? null : feedbackUserNickname.trim();
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}