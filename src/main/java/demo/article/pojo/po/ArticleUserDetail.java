package demo.article.pojo.po;

import java.util.Date;

public class ArticleUserDetail {

	private Long articleChannelId;

	private Long userId;

	private Integer coefficient;

	private Integer dailyChannelPostLimit;

	private Integer dailyPostLimit;

	private Date createTime;

	private Date postLimitUpdateTime;

	private Boolean isFlash;

	private Date isFlashUpdateTime;

	private Integer likeChannelCount;

	private Date likeChannelUpdateTime;

	private Integer hateChannelCount;

	private Date hateChannelUpdateTime;

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

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public Integer getDailyChannelPostLimit() {
		return dailyChannelPostLimit;
	}

	public void setDailyChannelPostLimit(Integer dailyChannelPostLimit) {
		this.dailyChannelPostLimit = dailyChannelPostLimit;
	}

	public Integer getDailyPostLimit() {
		return dailyPostLimit;
	}

	public void setDailyPostLimit(Integer dailyPostLimit) {
		this.dailyPostLimit = dailyPostLimit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPostLimitUpdateTime() {
		return postLimitUpdateTime;
	}

	public void setPostLimitUpdateTime(Date postLimitUpdateTime) {
		this.postLimitUpdateTime = postLimitUpdateTime;
	}

	public Boolean getIsFlash() {
		return isFlash;
	}

	public void setIsFlash(Boolean isFlash) {
		this.isFlash = isFlash;
	}

	public Date getIsFlashUpdateTime() {
		return isFlashUpdateTime;
	}

	public void setIsFlashUpdateTime(Date isFlashUpdateTime) {
		this.isFlashUpdateTime = isFlashUpdateTime;
	}

	public Integer getLikeChannelCount() {
		return likeChannelCount;
	}

	public void setLikeChannelCount(Integer likeChannelCount) {
		this.likeChannelCount = likeChannelCount;
	}

	public Date getLikeChannelUpdateTime() {
		return likeChannelUpdateTime;
	}

	public void setLikeChannelUpdateTime(Date likeChannelUpdateTime) {
		this.likeChannelUpdateTime = likeChannelUpdateTime;
	}

	public Integer getHateChannelCount() {
		return hateChannelCount;
	}

	public void setHateChannelCount(Integer hateChannelCount) {
		this.hateChannelCount = hateChannelCount;
	}

	public Date getHateChannelUpdateTime() {
		return hateChannelUpdateTime;
	}

	public void setHateChannelUpdateTime(Date hateChannelUpdateTime) {
		this.hateChannelUpdateTime = hateChannelUpdateTime;
	}

	@Override
	public String toString() {
		return "ArticleUserDetail [articleChannelId=" + articleChannelId + ", userId=" + userId + ", coefficient="
				+ coefficient + ", dailyChannelPostLimit=" + dailyChannelPostLimit + ", dailyPostLimit="
				+ dailyPostLimit + ", createTime=" + createTime + ", postLimitUpdateTime=" + postLimitUpdateTime
				+ ", isFlash=" + isFlash + ", isFlashUpdateTime=" + isFlashUpdateTime + ", likeChannelCount="
				+ likeChannelCount + ", likeChannelUpdateTime=" + likeChannelUpdateTime + ", hateChannelCount="
				+ hateChannelCount + ", hateChannelUpdateTime=" + hateChannelUpdateTime + "]";
	}

}