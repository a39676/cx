package demo.ai.manager.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.manager.pojo.dto.GetAiUserListDTO;
import demo.ai.manager.pojo.result.GetAiUserListResult;

public interface AiUserManagerService {

	CommonResult blockUserByPk(String aiChatUserPk);

	CommonResult unlockUserByPk(String aiChatUserPk);

	GetAiUserListResult getAiChatUserList(GetAiUserListDTO dto);

}
