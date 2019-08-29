package demo.article.pojo.param.mapperParam;

public class FindArticleUserDetailByUserIdChannelIdParam {

	private Long userId;

	private Long channelId;

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

	@Override
	public String toString() {
		return "FindArticleUserDetailByUserIdChannelIdParam [userId=" + userId + ", channelId=" + channelId + "]";
	}

}
