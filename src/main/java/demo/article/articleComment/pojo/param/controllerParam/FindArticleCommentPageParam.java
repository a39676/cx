package demo.article.articleComment.pojo.param.controllerParam;

import java.util.Date;

public class FindArticleCommentPageParam {

	private String pk;
	private Date startTime;
	private Boolean hasAdminRole = false;
	private Boolean isPass = true;
	private Boolean isDelete = false;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
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
		return "FindArticleCommentPageParam [pk=" + pk + ", startTime=" + startTime + ", hasAdminRole=" + hasAdminRole
				+ "]";
	}

}
