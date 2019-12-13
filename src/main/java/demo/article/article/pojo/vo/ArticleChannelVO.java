package demo.article.article.pojo.vo;

public class ArticleChannelVO implements Comparable<ArticleChannelVO> {

	private String channelName;
	private String channelId;
	private String channelImage;
	private Integer weights;

	public String getChannelImage() {
		return channelImage;
	}

	public void setChannelImage(String channelImage) {
		this.channelImage = channelImage;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	@Override
	public String toString() {
		return "ArticleChannelVO [channelName=" + channelName + ", channelId=" + channelId + ", channelImage="
				+ channelImage + ", weights=" + weights + "]";
	}

	@Override
	public int compareTo(ArticleChannelVO o) {
		if (o.weights == null || this.weights == null) {
			if (o.weights == null && this.weights == null) {
				return 0;
			} else if (o.weights == null) {
				return -1;
			} else if (this.weights == null) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (this.weights > o.weights) {
				return -1;
			} else if (this.weights < o.weights) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
