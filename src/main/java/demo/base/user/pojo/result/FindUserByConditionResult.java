package demo.base.user.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.vo.UsersDetailVO;

public class FindUserByConditionResult extends CommonResult {

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
