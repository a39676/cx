package demo.base.user.pojo.dto;

import java.util.List;

import demo.base.user.pojo.type.RolesType;

public class FindUserAuthDTO {

	private String userPk;

	private String authPk;

	private List<RolesType> roleTypeList;

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getAuthPk() {
		return authPk;
	}

	public void setAuthPk(String authPk) {
		this.authPk = authPk;
	}

	public List<RolesType> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<RolesType> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	@Override
	public String toString() {
		return "FindUserAuthDTO [userPk=" + userPk + ", authPk=" + authPk + ", roleTypeList=" + roleTypeList + "]";
	}

}
