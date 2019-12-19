package demo.article.article.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class CheckParamBeforeEditArticleResult extends CommonResultCX {

	private Long channelId;
	private String storePrefixPath;
	private String summaryStorePrefixPath;
	private String title;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getStorePrefixPath() {
		return storePrefixPath;
	}

	public void setStorePrefixPath(String storePrefixPath) {
		this.storePrefixPath = storePrefixPath;
	}

	public String getSummaryStorePrefixPath() {
		return summaryStorePrefixPath;
	}

	public void setSummaryStorePrefixPath(String summaryStorePrefixPath) {
		this.summaryStorePrefixPath = summaryStorePrefixPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "CheckParamBeforeCreateArticleResult [channelId=" + channelId + ", storePrefixPath=" + storePrefixPath
				+ ", summaryStorePrefixPath=" + summaryStorePrefixPath + ", title=" + title + "]";
	}

}
