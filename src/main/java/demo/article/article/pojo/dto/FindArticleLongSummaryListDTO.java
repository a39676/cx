package demo.article.article.pojo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import toolPack.dateTimeHandle.DateHandler;

@ApiModel(value = "查询文章缩写标题参数", description = "")
public class FindArticleLongSummaryListDTO {

	@ApiModelProperty(value = "频道名")
	private String articleChannelName;
	@ApiModelProperty(value = "频道id")
	private Long articleChannelId;
	@ApiModelProperty(value = "title")
	private String title;
	private Long userId;
	@JsonFormat(pattern = DateHandler.normalDateTimeFormat)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime startTime;
	@JsonFormat(pattern = DateHandler.normalDateTimeFormat)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime endTime;
	private Boolean isDelete = false;
	private Boolean isPass = true;
	private Boolean isEdited = false;
	private Boolean isReject = false;
	private Integer limit = 1;
	private Boolean desc = true;
	private Boolean isHot = false;
	private String vcode;

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getArticleChannelName() {
		return articleChannelName;
	}

	public void setArticleChannelName(String articleChannelName) {
		this.articleChannelName = articleChannelName;
	}

	public Long getArticleChannelId() {
		return articleChannelId;
	}

	public void setArticleChannelId(Long articleChannelId) {
		this.articleChannelId = articleChannelId;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public Boolean getDesc() {
		return desc;
	}

	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "FindArticleLongSummaryListDTO [articleChannelName=" + articleChannelName + ", articleChannelId="
				+ articleChannelId + ", title=" + title + ", userId=" + userId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", isDelete=" + isDelete + ", isPass=" + isPass + ", isEdited=" + isEdited
				+ ", isReject=" + isReject + ", limit=" + limit + ", desc=" + desc + ", isHot=" + isHot + ", vcode="
				+ vcode + "]";
	}

}
