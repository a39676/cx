package demo.base.user.pojo.dto;

public class InsertAuthDTO {

	private String belongOrgPK;
	private String authName;

	public String getBelongOrgPK() {
		return belongOrgPK;
	}

	public void setBelongOrgPK(String belongOrgPK) {
		this.belongOrgPK = belongOrgPK;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Override
	public String toString() {
		return "InsertAuthDTO [belongOrgPK=" + belongOrgPK + ", authName=" + authName + "]";
	}


}
