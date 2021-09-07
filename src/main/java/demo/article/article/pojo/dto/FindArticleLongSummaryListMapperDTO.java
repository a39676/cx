package demo.article.article.pojo.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FindArticleLongSummaryListMapperDTO {

	private String title;
	private List<Long> channelIdList;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Boolean isDelete = false;
	private Boolean isPass = true;
	private Boolean isEdited = false;
	private Boolean isReject = false;
	private Boolean desc = true;
	private Integer limit;
	private Boolean isHot = false;

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Long> getChannelIdList() {
		return channelIdList;
	}

	public void setChannelIdList(List<Long> channelIdList) {
		this.channelIdList = channelIdList;
	}
	
	public void addChannelId(Long channelId) {
		if(this.channelIdList == null) {
			this.channelIdList = new ArrayList<Long>();
		}
		this.channelIdList.add(channelId);
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

	@Override
	public String toString() {
		return "FindArticleLongSummaryListMapperDTO [title=" + title + ", channelIdList=" + channelIdList
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isDelete=" + isDelete + ", isPass=" + isPass
				+ ", isEdited=" + isEdited + ", isReject=" + isReject + ", desc=" + desc + ", limit=" + limit
				+ ", isHot=" + isHot + "]";
	}

}
