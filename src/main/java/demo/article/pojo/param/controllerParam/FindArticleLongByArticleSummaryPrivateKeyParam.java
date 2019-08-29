package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class FindArticleLongByArticleSummaryPrivateKeyParam implements CommonControllerParam {

	private Long articleId;

	private String privateKey;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isEdited;

	private Boolean isReject;

	private Long userId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FindArticleLongByArticleSummaryPrivateKeyParam [articleId=" + articleId + ", privateKey=" + privateKey
				+ ", isDelete=" + isDelete + ", isPass=" + isPass + ", isEdited=" + isEdited + ", isReject=" + isReject
				+ ", userId=" + userId + "]";
	}

	@Override
	public FindArticleLongByArticleSummaryPrivateKeyParam fromJson(JSONObject json) {
		FindArticleLongByArticleSummaryPrivateKeyParam param = new FindArticleLongByArticleSummaryPrivateKeyParam();
		if (json.containsKey("pk")) {
			param.setPrivateKey(json.getString("pk"));
		}
		if (json.containsKey("isDelete")) {
			if (json.getString("isDelete").toLowerCase().equals("true") || json.getString("isDelete").equals("1")) {
				param.setIsDelete(true);
			} else {
				param.setIsDelete(false);
			}
		}
		if (json.containsKey("isPass")) {
			if (json.getString("isPass").toLowerCase().equals("true") || json.getString("isPass").equals("1")) {
				param.setIsPass(true);
			} else {
				param.setIsPass(false);
			}
		}
		if (json.containsKey("isEdited")) {
			if (json.getString("isEdited").toLowerCase().equals("true") || json.getString("isEdited").equals("1")) {
				param.setIsEdited(true);
			} else {
				param.setIsEdited(false);
			}
		}
		if (json.containsKey("isReject")) {
			if (json.getString("isReject").toLowerCase().equals("true") || json.getString("isReject").equals("1")) {
				param.setIsReject(true);
			} else {
				param.setIsReject(false);
			}
		}
		return param;
	}

}
