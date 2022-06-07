package demo.article.article.pojo.bo;

import java.util.List;

import demo.article.article.pojo.po.ArticleLongSummary;

public class ArticleLongSummaryBO extends ArticleLongSummary {

	private Long articleId;

	private String articleTitle;

	private Long userId;

	private List<String> imgUrls;

	private String firstLine;

	private String createDateString;

	public List<String> getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(List<String> imgChannels) {
		this.imgUrls = imgChannels;
	}

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Override
	public String toString() {
		return "ArticleLongSummaryBO [articleId=" + articleId + ", articleTitle=" + articleTitle + ", userId=" + userId
				+ ", imgUrls=" + imgUrls + ", firstLine=" + firstLine + ", createDateString=" + createDateString + "]";
	}

}
