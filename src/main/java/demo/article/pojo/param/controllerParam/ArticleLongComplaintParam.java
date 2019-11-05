package demo.article.pojo.param.controllerParam;

import io.swagger.annotations.ApiModelProperty;

public class ArticleLongComplaintParam {
	
	@ApiModelProperty(value = "文章id")
	private String pk;
	@ApiModelProperty(hidden = true)
	private Long complaintUserId;
	@ApiModelProperty(value = "投诉描述/理由")
	private String complaintReason;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Long getComplaintUserId() {
		return complaintUserId;
	}

	public void setComplaintUserId(Long complaintUserId) {
		this.complaintUserId = complaintUserId;
	}

	public String getComplaintReason() {
		return complaintReason;
	}

	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}

	@Override
	public String toString() {
		return "ArticleLongComplaintParam [pk=" + pk + ", complaintUserId=" + complaintUserId + ", complaintReason="
				+ complaintReason + "]";
	}

}