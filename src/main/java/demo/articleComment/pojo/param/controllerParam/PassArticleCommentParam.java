package demo.articleComment.pojo.param.controllerParam;

public class PassArticleCommentParam  {

	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		return "PassArticleCommentParam [commentId=" + commentId + "]";
	}

}
