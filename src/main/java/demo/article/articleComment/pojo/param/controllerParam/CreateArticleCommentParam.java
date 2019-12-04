package demo.article.articleComment.pojo.param.controllerParam;

public class CreateArticleCommentParam {

	private String pk;

	private String content;

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

	@Override
	public String toString() {
		return "CreateArticleCommentParam [pk=" + pk + ", content=" + content + "]";
	}

}