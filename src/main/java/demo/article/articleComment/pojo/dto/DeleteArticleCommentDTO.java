package demo.article.articleComment.pojo.dto;

public class DeleteArticleCommentDTO  {

	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		return "DeleteArticleCommentParam [commentId=" + commentId + "]";
	}

}
