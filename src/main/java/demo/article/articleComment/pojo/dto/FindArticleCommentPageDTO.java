package demo.article.articleComment.pojo.dto;

import java.time.LocalDateTime;

public class FindArticleCommentPageDTO {

	private String pk;
	private LocalDateTime startTime;
	private Boolean hasAdminRole = false;
	private Boolean isPass = true;
	private Boolean isDelete = false;

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

	public Boolean getHasAdminRole() {
		return hasAdminRole;
	}

	public void setHasAdminRole(Boolean hasAdminRole) {
		this.hasAdminRole = hasAdminRole;
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

	@Override
	public String toString() {
		return "FindArticleCommentPageDTO [pk=" + pk + ", startTime=" + startTime + ", hasAdminRole=" + hasAdminRole
				+ ", isPass=" + isPass + ", isDelete=" + isDelete + "]";
	}

}
