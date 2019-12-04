package demo.article.article.pojo.param.mapperParam;

public class BatchUpdateFlashStatuParam {

	private boolean isFlash;

	private Long userId;

	private Long articleChannelId;

	private Integer dailyChannelPostLimit;

	public boolean isFlash() {
		return isFlash;
	}

	public void setFlash(boolean isFlash) {
		this.isFlash = isFlash;
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

	public Integer getDailyChannelPostLimit() {
		return dailyChannelPostLimit;
	}

	public void setDailyChannelPostLimit(Integer dailyChannelPostLimit) {
		this.dailyChannelPostLimit = dailyChannelPostLimit;
	}

	@Override
	public String toString() {
		return "BatchUpdateFlashStatuParam [isFlash=" + isFlash + ", userId=" + userId + ", articleChannelId="
				+ articleChannelId + ", dailyChannelPostLimit=" + dailyChannelPostLimit + "]";
	}

}
