package demo.article.articleComment.pojo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import toolPack.dateTimeHandle.DateHandler;

public class FindArticleCommentPageDTO {

	private String pk;
	@JsonFormat(pattern = DateHandler.normalDateTimeFormat)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime startTime;
	private Boolean isPass = true;
	private Boolean isDelete = false;
	private Boolean isReject = false;
	private Short limit = 10;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public Short getLimit() {
		return limit;
	}
	
	public void setLimit(Short limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return "FindArticleCommentPageDTO [pk=" + pk + ", startTime=" + startTime + ", isPass=" + isPass + ", isDelete="
				+ isDelete + ", isReject=" + isReject + ", limit=" + limit + "]";
	}

}
