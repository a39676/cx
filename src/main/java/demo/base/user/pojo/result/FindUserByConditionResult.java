package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.po.Users;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindUserByConditionResult extends CommonResultCX {

	private List<Users> userList;

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "FindUserByConditionResult [userList=" + userList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
