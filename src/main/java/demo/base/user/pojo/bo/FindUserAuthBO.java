package demo.base.user.pojo.bo;

import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class FindUserAuthBO {

	private Long userId;

	private Long authId;

	private List<RolesType> roleTypeList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public List<RolesType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RolesType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	@Override
	public String toString() {
		return "FindUserAuthBO [userId=" + userId + ", authId=" + authId + ", roleTypeList=" + roleTypeList + "]";
	}

}
