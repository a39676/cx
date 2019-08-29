package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class InsertArticleLongEvaluationParam implements CommonControllerParam {

	private String pk;

	private Integer evaluationType;

	private Integer evaluationCode;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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
		return "InsertEvaluationParam [pk=" + pk + ", evaluationType=" + evaluationType + ", evaluationCode="
				+ evaluationCode + "]";
	}

	@Override
	public InsertArticleLongEvaluationParam fromJson(JSONObject json) {
		if (json.containsKey("evaluationCode") && NumericUtilCustom.matchInteger(json.getString("evaluationCode"))) {
			this.evaluationCode = json.getInt("evaluationCode");
		}
		if (json.containsKey("pk")) {
			this.pk = json.getString("pk");
		}
		return this;
	}

}
