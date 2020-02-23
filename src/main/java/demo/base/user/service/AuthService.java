package demo.base.user.service;

import demo.base.user.pojo.bo.DeleteAuthRoleBO;
import demo.base.user.pojo.bo.FindAuthsBO;
import demo.base.user.pojo.dto.FindAuthsDTO;
import demo.base.user.pojo.dto.InsertNewAuthDTO;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindAuthsVOResult;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.pojo.type.AuthType;
import demo.base.user.pojo.vo.AuthVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface AuthService {

	Long __createBaseSuperAdminAuth(Long supserAdminUserId);

	Long __createBaseAdminAuth(Long supserAdminUserId);

	Long __createBaseUserActiveAuth(Long supserAdminUserId);

	Long __createBaseUserAuth(Long supserAdminUserId);

	Long __createBasePosterAuth(Long supserAdminUserId);

	Long __createBaseDelayPosterAuth(Long supserAdminUserId);

	FindAuthsResult findSuperAdministratorAuth();

	FindAuthsResult findAuthsByCondition(FindAuthsDTO dto);
	FindAuthsResult findAuthsByCondition(FindAuthsBO bo);
	FindAuthsResult findAuthsByCondition(AuthType authType);
	FindAuthsResult findAuthsByCondition(Long authId);

	FindAuthsVOResult findAuthVOListByCondition(FindAuthsDTO dto);

	AuthVO buildAuthVOByPO(Auth po);

	InsertNewAuthResult insertOrgAuth(InsertNewAuthDTO dto);

	InsertNewAuthResult insertSysAuth(InsertNewAuthDTO dto);

	/**
	 * 
	 * @param operatorId 当前操作者用户ID
	 * @param authId 拟加入 / 删除的角色 ID (可能为自己增删角色, 可能为其他用户增删角色)
	 * @return
	 */
	CommonResultCX canEditUserAuth(Long authId);

	CommonResultCX deleteAuth(DeleteAuthRoleBO bo);


}
