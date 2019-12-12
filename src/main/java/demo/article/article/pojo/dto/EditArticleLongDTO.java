package demo.article.article.pojo.dto;

public class EditArticleLongDTO {

	private String privateKey;
	private String uuid;
	private String title;
	private String content;

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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

	@Override
	public String toString() {
		return "EditArticleLongDTO [privateKey=" + privateKey + ", uuid=" + uuid + ", title=" + title + ", content="
				+ content + "]";
	}

}
