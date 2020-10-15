package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.vo.UsersDetailVO;
import demo.common.pojo.result.CommonResultCX;

public class FindUserByConditionResult extends CommonResultCX {

	private List<UsersDetailVO> userVOList;

	public List<UsersDetailVO> getUserVOList() {
		return userVOList;
	}

	public void setUserVOList(List<UsersDetailVO> userVOList) {
		this.userVOList = userVOList;
	}

	@Override
	public String toString() {
		return "FindUserByConditionResult [userVOList=" + userVOList + "]";
	}

}
