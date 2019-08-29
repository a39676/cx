package demo.article.pojo.param.mapperParam;

import java.time.LocalDateTime;

public class FindArticleLongSummaryPageMapperParam {

	private String title;
	private Long userId;
	private Integer articleChannelId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Boolean isDelete = false;
	private Boolean isPass = true;
	private Boolean isEdited = false;
	private Boolean isReject = false;
	private Boolean desc = true;
	private Integer pageStart = 1;
	private Integer pageSize = 10;
	private Boolean isHot = false;

	@Override
	public String toString() {
		return "FindArticleLongSummaryPageMapperParam [title=" + title + ", userId=" + userId + ", articleChannelId="
				+ articleChannelId + ", startTime=" + startTime + ", endTime=" + endTime + ", isDelete=" + isDelete
				+ ", isPass=" + isPass + ", isEdited=" + isEdited + ", isReject=" + isReject + ", desc=" + desc
				+ ", pageStart=" + pageStart + ", pageSize=" + pageSize + ", isHot=" + isHot + "]";
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Integer getArticleChannelId() {
		return articleChannelId;
	}

	public void setArticleChannelId(Integer articleChannelId) {
		this.articleChannelId = articleChannelId;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public Boolean getDesc() {
		return desc;
	}

	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

}
