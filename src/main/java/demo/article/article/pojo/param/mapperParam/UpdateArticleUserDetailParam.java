package demo.article.article.pojo.param.mapperParam;

import java.util.Date;

public class UpdateArticleUserDetailParam {

	private Boolean isFlash;

	private Integer coefficient;

	private Date isFlashUpdateTime;

	private Integer dailyChannelPostLimit;

	private Long userId;

	private Long articleChannelId;

	private Integer likeCount;

	private Integer hateCount;

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

	public Integer getDailyChannelPostLimit() {
		return dailyChannelPostLimit;
	}

	public void setDailyChannelPostLimit(Integer dailyChannelPostLimit) {
		this.dailyChannelPostLimit = dailyChannelPostLimit;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArticleChannelId() {
		return articleChannelId;
	}

	public void setArticleChannelId(Long articleChannelId) {
		this.articleChannelId = articleChannelId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getHateCount() {
		return hateCount;
	}

	public void setHateCount(Integer hateCount) {
		this.hateCount = hateCount;
	}

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return "UpdateArticleUserDetailParam [isFlash=" + isFlash + ", coefficient=" + coefficient
				+ ", isFlashUpdateTime=" + isFlashUpdateTime + ", dailyChannelPostLimit=" + dailyChannelPostLimit
				+ ", userId=" + userId + ", articleChannelId=" + articleChannelId + ", likeCount=" + likeCount
				+ ", hateCount=" + hateCount + "]";
	}

}
