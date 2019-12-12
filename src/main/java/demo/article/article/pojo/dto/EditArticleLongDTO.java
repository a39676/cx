package demo.article.article.pojo.dto;

public class EditArticleLongDTO {

	private Long articleId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "EditArticleLongDTO [articleId=" + articleId + "]";
	}

}
