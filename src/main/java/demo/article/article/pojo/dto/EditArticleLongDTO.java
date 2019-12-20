package demo.article.article.pojo.dto;

public class EditArticleLongDTO {

	private String pk;
	private String channelId;
	private String title;
	private String content;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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
		return "EditArticleLongDTO [pk=" + pk + ", channelId=" + channelId + ", title=" + title + ", content=" + content
				+ "]";
	}

}
