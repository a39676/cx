package demo.base.user.pojo.dto;

public class GetAuthManagerViewDTO {

	private String orgPK;

	public String getOrgPK() {
		return orgPK;
	}

	public void setOrgPK(String orgPK) {
		this.orgPK = orgPK;
	}

	@Override
	public String toString() {
		return "GetAuthManagerViewDTO [orgPK=" + orgPK + "]";
	}

}
