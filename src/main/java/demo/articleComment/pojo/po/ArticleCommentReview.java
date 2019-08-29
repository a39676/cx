package demo.articleComment.pojo.po;

import java.util.Date;

public class ArticleCommentReview {
    private Long commentId;

    private Long articleReviewerId;

    private Date createTime;

    private Long remarkId;

    private Integer reviewTypeId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getArticleReviewerId() {
        return articleReviewerId;
    }

    public void setArticleReviewerId(Long articleReviewerId) {
        this.articleReviewerId = articleReviewerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Integer getReviewTypeId() {
        return reviewTypeId;
    }

    public void setReviewTypeId(Integer reviewTypeId) {
        this.reviewTypeId = reviewTypeId;
    }
}