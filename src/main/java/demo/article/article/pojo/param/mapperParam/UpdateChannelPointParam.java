package demo.article.article.pojo.param.mapperParam;

public class UpdateChannelPointParam {

	private Long channelId;
	private int channelPoint = 0;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public int getChannelPoint() {
		return channelPoint;
	}

	public void setChannelPoint(int channelPoint) {
		this.channelPoint = channelPoint;
	}

	@Override
	public String toString() {
		return "UpdateChannelPointParam [channelId=" + channelId + ", channelPoint=" + channelPoint + "]";
	}

}
