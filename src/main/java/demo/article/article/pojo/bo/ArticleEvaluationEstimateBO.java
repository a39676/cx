package demo.article.article.pojo.bo;

public class ArticleEvaluationEstimateBO {

	private Long ArticleId;
	private Integer goodEvaluationCounter = 0;
	private Integer badEvaluationCounter = 0;
	private Boolean isGoodResult;

	public Long getArticleId() {
		return ArticleId;
	}

	public void setArticleId(Long articleId) {
		ArticleId = articleId;
	}

	public Integer getGoodEvaluationCounter() {
		return goodEvaluationCounter;
	}

	public void setGoodEvaluationCounter(Integer goodEvaluationCounter) {
		this.goodEvaluationCounter = goodEvaluationCounter;
	}
	
	public void addGoodEvaluationCounter(Integer addNum) {
		this.goodEvaluationCounter += addNum;
	}

	public Integer getBadEvaluationCounter() {
		return badEvaluationCounter;
	}

	public void setBadEvaluationCounter(Integer badEvaluationCounter) {
		this.badEvaluationCounter = badEvaluationCounter;
	}
	
	public void addBadEvaluationCounter(Integer addNum) {
		this.badEvaluationCounter += addNum;
	}

	public Boolean getIsGoodResult() {
		return isGoodResult;
	}

	public void setIsGoodResult(Boolean isGoodResult) {
		this.isGoodResult = isGoodResult;
	}
	
}
