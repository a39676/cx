package demo.article.pojo.result.jsonRespon;

import java.util.List;

import demo.baseCommon.pojo.result.CommonResult;

public class ArticleFileSaveResult extends CommonResult {

	private Long articleId;
	private String firstLine;
	private List<String> imageUrls;
	private String filePath;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "ArticleFileSaveResult [articleId=" + articleId + ", firstLine=" + firstLine + ", imageUrls=" + imageUrls
				+ ", filePath=" + filePath + "]";
	}

}
