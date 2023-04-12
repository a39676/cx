package demo.aiChat.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.pojo.vo.AiChatUserVO;

public class GetAiChatUserListResult extends CommonResult {

	private List<AiChatUserVO> userList;

	public List<AiChatUserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<AiChatUserVO> userList) {
		this.userList = userList;
	}

	@Override
	public String toString() {
		return "GetAiChatUserListResult [userList=" + userList + "]";
	}

}
