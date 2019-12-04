package demo.article.article.pojo.bo;

public class ArticleChannelUUIDBO {

	private String uuid;
	private Long channelId;
	private String channelName;
	private String image;
	private Integer weights;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	@Override
	public String toString() {
		return "ArticleChannelUUIDBO [uuid=" + uuid + ", channelId=" + channelId + ", channelName=" + channelName
				+ ", image=" + image + ", weights=" + weights + "]";
	}

}
