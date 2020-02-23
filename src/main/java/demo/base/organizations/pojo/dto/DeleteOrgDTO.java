package demo.base.organizations.pojo.dto;

public class DeleteOrgDTO {

	private String orgPk;

	public String getOrgPk() {
		return orgPk;
	}

	public void setOrgPk(String orgPk) {
		this.orgPk = orgPk;
	}

	@Override
	public String toString() {
		return "DeleteOrgDTO [orgPk=" + orgPk + "]";
	}

}
