package demo.article.article.pojo.param.mapperParam;

public class FindArticleChannelsParam {

	private String channelName;

	private Long channelId;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "FindArticleChannelsParam [channelName=" + channelName + ", channelId=" + channelId + "]";
	}

}
