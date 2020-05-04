package demo.article.articleComment.pojo.dto;

public class CreateArticleCommentDTO {

	private String pk;

	private String content;

	private String nickname;

	private String email;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CreateArticleCommentDTO [pk=" + pk + ", content=" + content + ", nickname=" + nickname + ", email="
				+ email + "]";
	}

}
