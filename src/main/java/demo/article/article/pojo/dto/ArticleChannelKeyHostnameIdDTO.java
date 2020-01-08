package demo.article.article.pojo.dto;

public class ArticleChannelKeyHostnameIdDTO {

	private Long channelId;
	private Integer hostnameId;
	private String hostname;
	/** ArticleChannelKeyHostnameType */
	private Integer articleChannelKeyHostnameType;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Integer getHostnameId() {
		return hostnameId;
	}

	public void setHostnameId(Integer hostnameId) {
		this.hostnameId = hostnameId;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getArticleChannelKeyHostnameType() {
		return articleChannelKeyHostnameType;
	}

	public void setArticleChannelKeyHostnameType(Integer articleChannelKeyHostnameType) {
		this.articleChannelKeyHostnameType = articleChannelKeyHostnameType;
	}

	@Override
	public String toString() {
		return "ArticleChannelKeyHostnameIdDTO [channelId=" + channelId + ", hostnameId=" + hostnameId + ", hostname="
				+ hostname + ", articleChannelKeyHostnameType=" + articleChannelKeyHostnameType + "]";
	}

}
