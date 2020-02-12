package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.vo.UsersDetailForAdminVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindUserByConditionResult extends CommonResultCX {

	private List<UsersDetailForAdminVO> userList;

	public List<UsersDetailForAdminVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UsersDetailForAdminVO> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "FindUserByConditionResult [userList=" + userList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
