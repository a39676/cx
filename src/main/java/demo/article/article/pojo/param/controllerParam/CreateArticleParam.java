package demo.article.article.pojo.param.controllerParam;

public class CreateArticleParam  {

	private String superAdminKey;
	private String uuid;
	private String title;
	private String content;
	private boolean quickPass = false;

	public String getSuperAdminKey() {
		return superAdminKey;
	}

	public void setSuperAdminKey(String superAdminKey) {
		this.superAdminKey = superAdminKey;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public boolean getQuickPass() {
		return quickPass;
	}

	public void setQuickPass(boolean quickPass) {
		this.quickPass = quickPass;
	}

	@Override
	public String toString() {
		return "CreateArticleParam [superAdminKey=" + superAdminKey + ", uuid=" + uuid + ", title=" + title
				+ ", content=" + content + ", quickPass=" + quickPass + "]";
	}

}
