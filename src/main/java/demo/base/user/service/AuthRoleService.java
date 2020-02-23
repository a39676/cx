package demo.base.user.service;

import demo.base.user.pojo.bo.DeleteAuthRoleBO;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindRolesResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface AuthRoleService {

	Long insertAuthRole(Long authId, Long roleId, Long creatorId);

	FindAuthRoleResult findAuthRole(FindAuthRoleDTO dto);

	FindRolesResult findRolesByCondition(FindRolesDTO dto);

	/**
	 * 此处不做权限校验, 交给前置逻辑
	 * @param dto
	 * @return
	 */
	CommonResultCX deleteAuthRole(DeleteAuthRoleBO bo);

}
