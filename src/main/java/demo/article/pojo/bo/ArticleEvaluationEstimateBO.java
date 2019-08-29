package demo.article.pojo.bo;

import demo.article.pojo.constant.ArticleConstant;

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
	
	public void initIsGoodResult() {
		// 如果评价过于接近. 不作coefficient变动
		
		if(goodEvaluationCounter + badEvaluationCounter < ArticleConstant.miniEvaluationCount) {
			if(goodEvaluationCounter > badEvaluationCounter) {
				isGoodResult = true;
			} else {
				isGoodResult = false;
			}
		} else {
			if(goodEvaluationCounter == 0 && badEvaluationCounter > 0) {
				isGoodResult = false;
			} else if(badEvaluationCounter == 0 && goodEvaluationCounter > 0) {
				isGoodResult = true;
			} else if(goodEvaluationCounter > badEvaluationCounter && ((goodEvaluationCounter + 0D) / badEvaluationCounter > ArticleConstant.balanceEvaluationRatio)) {
				isGoodResult = true;
			} else if(badEvaluationCounter > goodEvaluationCounter && ((badEvaluationCounter + 0D) / goodEvaluationCounter > ArticleConstant.balanceEvaluationRatio)) {
				isGoodResult = false;
			}
		}
	}

}
