package demo.base.user.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.vo.RoleVO;

public class FindRolesVOResult extends CommonResult {

	private List<RoleVO> roleVOList;

	public List<RoleVO> getRoleVOList() {
		return roleVOList;
	}

	public void setRoleVOList(List<RoleVO> roleVOList) {
		this.roleVOList = roleVOList;
	}

	@Override
	public String toString() {
		return "FindRolesVOResult [roleVOList=" + roleVOList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", isFail()="
				+ isFail() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}

}
