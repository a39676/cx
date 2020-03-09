package demo.base.user.service;

import demo.base.user.pojo.bo.BatchDeleteAuthRoleBO;
import demo.base.user.pojo.dto.EditAuthRoleByRoleNameDTO;
import demo.base.user.pojo.dto.EditAuthRoleDTO;
import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindRolesResult;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface AuthRoleService {

	/**
	 * 只提供给系统初始化时使用
	 * 未对操作者进行权限校验
	 * @param authId
	 * @param roleId
	 * @param creatorId
	 * @return
	 */
	Long __insertAuthRole(Long authId, Long roleId, Long creatorId);

	FindAuthRoleResult findAuthRole(FindAuthRoleDTO dto);

	FindRolesResult findRolesByCondition(FindRolesDTO dto);

	/**
	 * 此处不做权限校验, 交给前置逻辑
	 * @param dto
	 * @return
	 */
	CommonResultCX batchDeleteAuthRole(BatchDeleteAuthRoleBO bo);

	CommonResultCX insertAuthRole(EditAuthRoleDTO dto);
	CommonResultCX insertAuthRole(EditAuthRoleByRoleNameDTO dto);

	CommonResultCX deleteAuthRole(EditAuthRoleDTO dto);


}
