package demo.interaction.wechat.pojo.vo;

public class WechatQrcodeVO {

	private String pk;

	private Integer sourceOfficialAccount;

	private String sceneName;

	private String remark;

	private String url;

	private String createTimeStr;

	private Boolean isDelete;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getSourceOfficialAccount() {
		return sourceOfficialAccount;
	}

	public void setSourceOfficialAccount(Integer sourceOfficialAccount) {
		this.sourceOfficialAccount = sourceOfficialAccount;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "WechatQrcodeVO [pk=" + pk + ", sourceOfficialAccount=" + sourceOfficialAccount + ", sceneName="
				+ sceneName + ", remark=" + remark + ", url=" + url + ", createTimeStr=" + createTimeStr + ", isDelete="
				+ isDelete + "]";
	}

}
