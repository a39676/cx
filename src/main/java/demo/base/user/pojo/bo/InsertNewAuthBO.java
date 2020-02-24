package demo.base.user.pojo.bo;

import demo.base.user.pojo.dto.InsertAuthDTO;
import demo.base.user.pojo.type.AuthTypeType;

public class InsertNewAuthBO extends InsertAuthDTO {

	private Long creatorId;
	private AuthTypeType authTypeType;

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public AuthTypeType getAuthTypeType() {
		return authTypeType;
	}

	public void setAuthTypeType(AuthTypeType authTypeType) {
		this.authTypeType = authTypeType;
	}

	@Override
	public String toString() {
		return "InsertNewAuthBO [creatorId=" + creatorId + ", authTypeType=" + authTypeType + ", getBelongOrgId()="
				+ getBelongOrgId() + ", getAuthName()=" + getAuthName() + ", getSysRoles()=" + getSysRoles()
				+ ", getOrgRoles()=" + getOrgRoles() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}
