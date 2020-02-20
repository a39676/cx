package demo.base.user.pojo.result;

import java.util.List;

import demo.base.user.pojo.vo.AuthVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindAuthsVOResult extends CommonResultCX {

	private List<AuthVO> authVOList;

	public List<AuthVO> getAuthVOList() {
		return authVOList;
	}

	public void setAuthVOList(List<AuthVO> authVOList) {
		this.authVOList = authVOList;
	}

	@Override
	public String toString() {
		return "FindAuthsResult [authVOList=" + authVOList + "]";
	}

}
