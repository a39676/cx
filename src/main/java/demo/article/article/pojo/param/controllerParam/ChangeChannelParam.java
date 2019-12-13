package demo.article.article.pojo.param.controllerParam;

public class ChangeChannelParam {

	private String pk;
	private Long channelId;
	private Long articleId;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "ChangeChannelParam [pk=" + pk + ", channelId=" + channelId + ", articleId=" + articleId + "]";
	}

}