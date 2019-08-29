package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class ReviewArticleLongParam implements CommonControllerParam {

	private String pk;
	private Integer reviewCode;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(Integer reviewCode) {
		this.reviewCode = reviewCode;
	}

	@Override
	public ReviewArticleLongParam fromJson(JSONObject json) {
		ReviewArticleLongParam param = new ReviewArticleLongParam();
		if (json.containsKey("reviewCode") && NumericUtilCustom.matchInteger(json.getString("reviewCode"))) {
			param.setReviewCode(json.getInt("reviewCode"));
		}
		if (json.containsKey("pk")) {
			param.setPk(json.getString("pk"));
		}
		
		return param;
	}

}
