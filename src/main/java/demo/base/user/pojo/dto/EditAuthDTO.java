package demo.base.user.pojo.dto;

public class EditAuthDTO {

	private String authPK;
	private String authName;

	public String getAuthPK() {
		return authPK;
	}

	public void setAuthPK(String authPK) {
		this.authPK = authPK;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Override
	public String toString() {
		return "EditAuthDTO [authPK=" + authPK + ", authName=" + authName + "]";
	}

}
