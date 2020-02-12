package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.dto.EditUserAuthDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserAuthService {

	CommonResultCX insertUserAuth(Long userId, Long authId);

	CommonResultCX insertBaseUserAuth(Long userId, AuthType authType);

	CommonResultCX deleteUserAuth(Long userId, Long authId);

	CommonResultCX deleteUserAuth(Long userId, AuthType authType);

	CommonResultCX isActiveUser(Long userId);

	CommonResultCX hasActiveUser(List<Long> userIdList);

	FindUserAuthResult findUserAuth(FindUserAuthDTO dto);

	CommonResultCX editUserAuth(EditUserAuthDTO dto);

}
