package demo.base.user.pojo.bo;

import demo.base.user.pojo.dto.InsertNewAuthDTO;

public class InsertNewAuthBO extends InsertNewAuthDTO {

	private Long creatorId;

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String toString() {
		return "InsertNewAuthBO [creatorId=" + creatorId + ", getBelongOrgId()=" + getBelongOrgId() + ", getAuthName()="
				+ getAuthName() + ", getSysRoles()=" + getSysRoles() + ", getOrgRoles()=" + getOrgRoles()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
