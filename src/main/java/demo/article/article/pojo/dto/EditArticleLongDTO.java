package demo.article.article.pojo.dto;

public class EditArticleLongDTO {

	private String privateKey;
	private String channelId;
	private String title;
	private String content;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "EditArticleLongDTO [privateKey=" + privateKey + ", channelId=" + channelId + ", title=" + title
				+ ", content=" + content + "]";
	}

}
