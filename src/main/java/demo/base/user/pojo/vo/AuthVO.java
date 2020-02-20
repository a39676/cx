package demo.base.user.pojo.vo;

public class AuthVO {

	private String pk;

	private String authName;

	private Integer authType;

	private Long belongOrg;

	private Boolean isDelete;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Long getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(Long belongOrg) {
		this.belongOrg = belongOrg;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "AuthVO [pk=" + pk + ", authName=" + authName + ", authType=" + authType + ", belongOrg=" + belongOrg
				+ ", isDelete=" + isDelete + "]";
	}

}
