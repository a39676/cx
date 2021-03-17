package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.vo.AuthVO;
import demo.base.user.pojo.vo.UserAuthVO;
import demo.common.pojo.result.CommonResultCX;

public class FindUserAuthVOResult extends CommonResultCX {

	private List<UserAuthVO> userAuthVOList;

	private List<AuthVO> authVOList;

	public List<UserAuthVO> getUserAuthVOList() {
		return userAuthVOList;
	}

	public void setUserAuthVOList(List<UserAuthVO> userAuthVOList) {
		this.userAuthVOList = userAuthVOList;
	}

	public List<AuthVO> getAuthVOList() {
		return authVOList;
	}

	public void setAuthVOList(List<AuthVO> authVOList) {
		this.authVOList = authVOList;
	}

	@Override
	public String toString() {
		return "FindUserAuthVOResult [userAuthVOList=" + userAuthVOList + ", authVOList=" + authVOList + "]";
	}

}
