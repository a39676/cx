package demo.article.article.pojo.dto;

public class ArticleFeedbackDTO {

	private String pk;
	private String feedback;
	private String nickname;
	private String mobile;
	private String email;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ArticleFeedbackDTO [pk=" + pk + ", feedback=" + feedback + ", nickname=" + nickname + ", mobile="
				+ mobile + ", email=" + email + "]";
	}

}
