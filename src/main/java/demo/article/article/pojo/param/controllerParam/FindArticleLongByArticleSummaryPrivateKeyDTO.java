package demo.article.article.pojo.param.controllerParam;

public class FindArticleLongByArticleSummaryPrivateKeyDTO {

	private String privateKey;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isEdited;

	private Boolean isReject;

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

	@Override
	public String toString() {
		return "FindArticleLongByArticleSummaryPrivateKeyDTO [privateKey=" + privateKey + ", isDelete=" + isDelete
				+ ", isPass=" + isPass + ", isEdited=" + isEdited + ", isReject=" + isReject + "]";
	}

}
