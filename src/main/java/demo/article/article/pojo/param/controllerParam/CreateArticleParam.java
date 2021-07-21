package demo.article.article.pojo.param.controllerParam;

public class CreateArticleParam {

	private String superAdminKey;
	private String channelId;
	private String title;
	private String content;

	public String getSuperAdminKey() {
		return superAdminKey;
	}

	public void setSuperAdminKey(String superAdminKey) {
		this.superAdminKey = superAdminKey;
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
		return "CreateArticleParam [superAdminKey=" + superAdminKey + ", channelId=" + channelId + ", title=" + title
				+ ", content=" + content + "]";
	}

}
