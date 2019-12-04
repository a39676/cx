package demo.article.article.pojo.param.mapperParam;

public class InsertOrUpdateUserDailyPostLimitParam {

	private Long articleChannelId;
	private Long userId;
	private Integer dailyChannelPostLimit;
	private Integer dailyPostLimit;

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

	@Override
	public String toString() {
		return "InsertOrUpdateUserDailyPostLimitParam [articleChannelId=" + articleChannelId + ", userId=" + userId
				+ ", dailyChannelPostLimit=" + dailyChannelPostLimit + ", dailyPostLimit=" + dailyPostLimit + "]";
	}

}
