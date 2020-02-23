package demo.base.user.pojo.bo;

import java.util.List;

public class DeleteAuthRoleBO {

	private Long orgId;
	private List<Long> authIdList;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<Long> getAuthIdList() {
		return authIdList;
	}

	public void setAuthIdList(List<Long> authIdList) {
		this.authIdList = authIdList;
	}

	@Override
	public String toString() {
		return "DeleteAuthRoleBO [orgId=" + orgId + ", authIdList=" + authIdList + "]";
	}

}
