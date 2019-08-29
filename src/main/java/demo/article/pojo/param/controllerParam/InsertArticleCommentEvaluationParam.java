package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class InsertArticleCommentEvaluationParam implements CommonControllerParam {

	private Long commentId;

	private Integer evaluationType;

	private Integer evaluationCode;

	


	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	@Override
	public String toString() {
		return "InsertArticleCommentEvaluationParam [commentId=" + commentId + ", evaluationType=" + evaluationType
				+ ", evaluationCode=" + evaluationCode + "]";
	}

	@Override
	public InsertArticleCommentEvaluationParam fromJson(JSONObject json) {
		if (json.containsKey("evaluationCode") && NumericUtilCustom.matchInteger(json.getString("evaluationCode"))) {
			this.evaluationCode = json.getInt("evaluationCode");
		}
		if (json.containsKey("commentId") && NumericUtilCustom.matchInteger(json.getString("commentId"))) {
			this.commentId = json.getLong("commentId");
		}
		return this;
	}

}
