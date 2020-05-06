package demo.article.articleComment.pojo.dto;

import java.time.LocalDateTime;

public class FindArticleCommentPageDTO {

	private String pk;
	private LocalDateTime startTime;
	private Boolean isPass = true;
	private Boolean isDelete = false;
	private Boolean isReject = false;

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

	@Override
	public String toString() {
		return "FindArticleCommentPageDTO [pk=" + pk + ", startTime=" + startTime + ", isPass=" + isPass + ", isDelete="
				+ isDelete + ", isReject=" + isReject + "]";
	}

}
