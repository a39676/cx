package demo.article.pojo.bo;

import java.util.List;

import demo.article.pojo.po.ArticleLongSummary;

public class ArticleLongSummaryBO extends ArticleLongSummary {

	private Long articleId;

	private String nickName;

	private String headImage;

	private Long userId;

	private List<String> imgUrls;

	private String firstLine;

	private String createDateString;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "ArticleLongSummaryBO [articleId=" + articleId + ", nickName=" + nickName + ", headImage=" + headImage
				+ ", userId=" + userId + ", imgUrls=" + imgUrls + ", firstLine=" + firstLine + ", createDateString="
				+ createDateString + "]";
	}

}
