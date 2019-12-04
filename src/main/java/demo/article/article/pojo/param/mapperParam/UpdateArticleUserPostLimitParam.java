package demo.article.article.pojo.param.mapperParam;

public class UpdateArticleUserPostLimitParam {

	private Integer newPostLimit;

	private Long userId;

	private Long channelId;

	public Integer getNewPostLimit() {
		return newPostLimit;
	}

	public void setNewPostLimit(Integer newPostLimit) {
		this.newPostLimit = newPostLimit;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
