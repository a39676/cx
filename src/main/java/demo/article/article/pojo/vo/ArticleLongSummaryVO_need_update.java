package demo.article.article.pojo.vo;

import java.util.HashMap;
import java.util.List;

public class ArticleLongSummaryVO_need_update {
	private String articleTitle;

	private String nickName;

	private Long userId;

	private List<String> imgUrls;

	private String firstLine;

	private String createDateString;

	private String createDateDescription;

	private String privateKey;

	private HashMap<Integer, ArticleEvaluationCounterVO> evaluationMap;
	
	private Boolean hasCommentNotReview = false;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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

	public HashMap<Integer, ArticleEvaluationCounterVO> getEvaluationMap() {
		return evaluationMap;
	}

	public void setEvaluationMap(HashMap<Integer, ArticleEvaluationCounterVO> evaluationMap) {
		this.evaluationMap = evaluationMap;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getCreateDateDescription() {
		return createDateDescription;
	}

	public void setCreateDateDescription(String createDateDescription) {
		this.createDateDescription = createDateDescription;
	}
	
	

	public Boolean getHasCommentNotReview() {
		return hasCommentNotReview;
	}

	public void setHasCommentNotReview(Boolean hasCommentNotReview) {
		this.hasCommentNotReview = hasCommentNotReview;
	}

	@Override
	public String toString() {
		return "ArticleLongSummaryVO [articleTitle=" + articleTitle + ", nickName=" + nickName + ", userId=" + userId
				+ ", imgUrls=" + imgUrls + ", firstLine=" + firstLine + ", createDateString=" + createDateString
				+ ", createDateDescription=" + createDateDescription + ", privateKey=" + privateKey + ", evaluationMap="
				+ evaluationMap + ", hasCommentNotReview=" + hasCommentNotReview + "]";
	}

}
