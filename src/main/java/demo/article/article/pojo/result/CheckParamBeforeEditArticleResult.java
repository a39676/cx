package demo.article.article.pojo.result;

import demo.common.pojo.result.CommonResultCX;

public class CheckParamBeforeEditArticleResult extends CommonResultCX {

	private Long channelId;
	private String title;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "CheckParamBeforeEditArticleResult [channelId=" + channelId + ", title=" + title + "]";
	}

}
