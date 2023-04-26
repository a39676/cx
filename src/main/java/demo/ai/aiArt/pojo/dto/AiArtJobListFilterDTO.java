package demo.ai.aiArt.pojo.dto;

public class AiArtJobListFilterDTO {

	private String lastJobPk;
	private String orderBy;
	private Boolean hasReview = true;
	private Boolean isFreeJob;
	private Integer jobStatus;
	private String createTimeStartStr;
	private String createTimeEndStr;
	private Boolean isFromApi;

	public String getLastJobPk() {
		return lastJobPk;
	}

	public void setLastJobPk(String lastJobPk) {
		this.lastJobPk = lastJobPk;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean getHasReview() {
		return hasReview;
	}

	public void setHasReview(Boolean hasReview) {
		this.hasReview = hasReview;
	}

	public Boolean getIsFreeJob() {
		return isFreeJob;
	}

	public void setIsFreeJob(Boolean isFreeJob) {
		this.isFreeJob = isFreeJob;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCreateTimeStartStr() {
		return createTimeStartStr;
	}

	public void setCreateTimeStartStr(String createTimeStartStr) {
		this.createTimeStartStr = createTimeStartStr;
	}

	public String getCreateTimeEndStr() {
		return createTimeEndStr;
	}

	public void setCreateTimeEndStr(String createTimeEndStr) {
		this.createTimeEndStr = createTimeEndStr;
	}

	public Boolean getIsFromApi() {
		return isFromApi;
	}

	public void setIsFromApi(Boolean isFromApi) {
		this.isFromApi = isFromApi;
	}

	@Override
	public String toString() {
		return "AiArtJobListFilterDTO [lastJobPk=" + lastJobPk + ", orderBy=" + orderBy + ", hasReview=" + hasReview
				+ ", isFreeJob=" + isFreeJob + ", jobStatus=" + jobStatus + ", createTimeStartStr=" + createTimeStartStr
				+ ", createTimeEndStr=" + createTimeEndStr + ", isFromApi=" + isFromApi + "]";
	}

}
