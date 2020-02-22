package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.bo.EditUserAuthBO;
import demo.base.user.pojo.bo.FindUserAuthBO;
import demo.base.user.pojo.dto.EditUserAuthDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.result.FindUserAuthVOResult;
import demo.base.user.pojo.type.AuthType;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserAuthService {

	CommonResultCX insertBaseUserAuth(Long userId, AuthType authType);
	CommonResultCX insertUserAuth(EditUserAuthDTO dto);
	CommonResultCX insertUserAuth(EditUserAuthBO bo);

	CommonResultCX deleteUserAuth(EditUserAuthDTO dto);

	CommonResultCX isActiveUser(Long userId);

	CommonResultCX hasActiveUser(List<Long> userIdList);

	FindUserAuthResult findUserAuth(FindUserAuthDTO dto);
	FindUserAuthResult findUserAuth(FindUserAuthBO bo);

	FindUserAuthVOResult findUserAuthVO(FindUserAuthDTO dto);
	
}
