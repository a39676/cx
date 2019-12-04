package demo.article.article.pojo.param.controllerParam;

import io.swagger.annotations.ApiModelProperty;

public class ReviewArticleLongParam {

	private String pk;
	@ApiModelProperty(hidden = true)
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

}
