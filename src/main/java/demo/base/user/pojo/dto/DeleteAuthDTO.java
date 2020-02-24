package demo.base.user.pojo.dto;

import java.util.List;

public class DeleteAuthDTO {

	private String orgPk;
	private List<String> authPkList;

	public String getOrgPk() {
		return orgPk;
	}

	public void setOrgPk(String orgPk) {
		this.orgPk = orgPk;
	}

	public List<String> getAuthPkList() {
		return authPkList;
	}

	public void setAuthPkList(List<String> authPkList) {
		this.authPkList = authPkList;
	}

	@Override
	public String toString() {
		return "DeleteAuthDTO [orgPk=" + orgPk + ", authPkList=" + authPkList + "]";
	}

}
