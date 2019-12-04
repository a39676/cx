package demo.article.articleComment.pojo.bo;

public class ArticleCommentCountByArticleIdBO {

	private Long articleId;

	private Integer commentCount;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	@Override
	public String toString() {
		return "ArticleCommentCountByArticleIdBO [articleId=" + articleId + ", commentCount=" + commentCount + "]";
	}

}
