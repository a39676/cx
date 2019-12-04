package demo.article.article.pojo.param.mapperParam;

public class UpdateChannelPointByArticleIdParam {

	private Long articleId;
	private int channelPoint = 0;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public int getChannelPoint() {
		return channelPoint;
	}

	public void setChannelPoint(int channelPoint) {
		this.channelPoint = channelPoint;
	}

	@Override
	public String toString() {
		return "UpdateChannelPointParam [articleId=" + articleId + ", channelPoint=" + channelPoint + "]";
	}

}
