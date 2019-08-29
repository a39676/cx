package demo.articleComment.pojo.param.controllerParam;

import java.util.Date;

import dateTimeHandle.DateUtilCustom;
import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class FindArticleCommentPageParam implements CommonControllerParam {

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

	@Override
	public FindArticleCommentPageParam fromJson(JSONObject json) {
		FindArticleCommentPageParam param = new FindArticleCommentPageParam();
		if (json.containsKey("pk")) {
			param.setPk(json.getString("pk"));
		}
		if (json.containsKey("startTime") && DateUtilCustom.isDateValid(json.getString("startTime"))) {
			param.setStartTime(DateUtilCustom.stringToDateUnkonwFormat(json.getString("startTime")));
		}
		if (json.containsKey("isPass") && "0".equals(json.getString("isPass"))) {
			param.setIsPass(false);
		}
		if (json.containsKey("isDelete") && "1".equals(json.getString("isDelete"))) {
			param.setIsDelete(true);
		}
		return param;
	}

}
