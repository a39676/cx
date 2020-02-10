package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.type.AuthType;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserAuthService {

	Long insertUserAuth(Long userId, Long authId);

	Long insertBaseUserAuth(Long userId, AuthType authType);

	int deleteUserAuth(Long userId, Long authId);

	int deleteUserAuth(Long userId, AuthType authType);

	CommonResultCX isActiveUser(Long userId);

	CommonResultCX hasActiveUser(List<Long> userIdList);

}
