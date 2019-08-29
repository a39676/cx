package demo.article.pojo.po;

import java.util.Date;

public class ArticleLongReview {
	private Long articleId;

	private Long articleCreatorId;

	private Long articleReviewerId;

	private Date createTime;

	private Long remark_id;

	private Integer review_type_id;

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

	public Long getRemark_id() {
		return remark_id;
	}

	public void setRemark_id(Long remark_id) {
		this.remark_id = remark_id;
	}

	public Integer getReview_type_id() {
		return review_type_id;
	}

	public void setReview_type_id(Integer review_type_id) {
		this.review_type_id = review_type_id;
	}

	@Override
	public String toString() {
		return "ArticleLongReview [articleId=" + articleId + ", articleCreatorId=" + articleCreatorId
				+ ", articleReviewerId=" + articleReviewerId + ", createTime=" + createTime + ", remark_id=" + remark_id
				+ ", review_type_id=" + review_type_id + "]";
	}

}