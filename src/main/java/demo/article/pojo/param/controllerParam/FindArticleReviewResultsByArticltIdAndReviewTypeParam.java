package demo.article.pojo.param.controllerParam;

import java.util.Date;

import dateTimeHandle.DateUtilCustom;
import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class FindArticleReviewResultsByArticltIdAndReviewTypeParam implements CommonControllerParam {

	private Long articleId;

	private Long articleCreatorId;
	
	private Long articleReviewerId;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer reviewTypeId;
	
	private Long remarkId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleCreatorId() {
		return articleCreatorId;
	}

	public void setArticleCreatorId(Long articleCreatorId) {
		this.articleCreatorId = articleCreatorId;
	}

	public Long getArticleReviewerId() {
		return articleReviewerId;
	}

	public void setArticleReviewerId(Long articleReviewerId) {
		this.articleReviewerId = articleReviewerId;
	}

	public Integer getReviewTypeId() {
		return reviewTypeId;
	}

	public void setReviewTypeId(Integer reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public FindArticleReviewResultsByArticltIdAndReviewTypeParam fromJson(JSONObject json) {
		FindArticleReviewResultsByArticltIdAndReviewTypeParam param = new FindArticleReviewResultsByArticltIdAndReviewTypeParam();
		if (json.containsKey("articleId") && NumericUtilCustom.matchInteger(json.getString("articleId"))) {
			param.setArticleId(Long.parseLong(json.getString("articleId")));
		}
		if (json.containsKey("articleCreatorId") && NumericUtilCustom.matchInteger(json.getString("ararticleCreatorIdticleId"))) {
			param.setArticleCreatorId(Long.parseLong(json.getString("articleCreatorId")));
		}
		if (json.containsKey("articleReviewerId") && NumericUtilCustom.matchInteger(json.getString("articleReviewerId"))) {
			param.setArticleReviewerId(Long.parseLong(json.getString("articleReviewerId")));
		}
		if (json.containsKey("reviewTypeId") && NumericUtilCustom.matchInteger(json.getString("reviewTypeId"))) {
			param.setReviewTypeId(Integer.parseInt(json.getString("reviewTypeId")));
		}
		if (json.containsKey("remarkId") && NumericUtilCustom.matchInteger(json.getString("remarkId"))) {
			param.setRemarkId(Long.parseLong(json.getString("remarkId")));
		}
		if (json.containsKey("startTime") && DateUtilCustom.isDateValid(json.getString("startTime"))) {
			param.setStartTime(DateUtilCustom.stringToDateUnkonwFormat(json.getString("startTime")));
		}
		if (json.containsKey("endTime") && DateUtilCustom.isDateValid(json.getString("endTime"))) {
			param.setStartTime(DateUtilCustom.stringToDateUnkonwFormat(json.getString("endTime")));
		}
		return param;
	}

}
