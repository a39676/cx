package demo.article.article.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class CheckParamBeforeEditArticleResult extends CommonResult {

	private Long channelId;
	private String title;
	private Boolean editFlag;

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

	public Boolean getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Boolean editFlag) {
		this.editFlag = editFlag;
	}

	@Override
	public String toString() {
		return "CheckParamBeforeEditArticleResult [channelId=" + channelId + ", title=" + title + ", editFlag="
				+ editFlag + "]";
	}

}
