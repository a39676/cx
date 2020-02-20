package demo.base.organizations.pojo.dto;

public class FindUserControlOrgDTO {

	private Long userId;
	private Long orgId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "FindUserControlOrgDTO [userId=" + userId + ", orgId=" + orgId + "]";
	}

}
