package demo.base.user.service;

import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.dto.InsertNewAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindAuthsVOResult;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.vo.AuthVO;

public interface AuthService {

	Long __createBaseSuperAdminAuth(Long supserAdminUserId);

	Long __createBaseAdminAuth(Long supserAdminUserId);

	Long __createBaseUserActiveAuth(Long supserAdminUserId);

	Long __createBaseUserAuth(Long supserAdminUserId);

	Long __createBasePosterAuth(Long supserAdminUserId);

	Long __createBaseDelayPosterAuth(Long supserAdminUserId);

	FindAuthsResult findSuperAdministratorAuth();

	FindAuthsResult findAuthsByCondition(FindAuthsDTO dto);
	FindAuthsResult findAuthsByCondition(AuthType authType);
	FindAuthsResult findAuthsByCondition(Long authId);

	FindAuthsVOResult findAuthVOListByCondition(FindAuthsDTO dto);

	AuthVO buildAuthVOByPO(Auth po);

	InsertNewAuthResult insertOrgAuth(InsertNewAuthDTO dto);

	InsertNewAuthResult insertSysAuth(InsertNewAuthDTO dto);

}
