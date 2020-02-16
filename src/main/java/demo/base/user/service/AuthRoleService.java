package demo.base.user.service;

import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.result.FindAuthRoleResult;
import demo.base.user.pojo.result.FindRolesResult;

public interface AuthRoleService {

	Long insertAuthRole(Long authId, Long roleId, Long creatorId);

	int deleteById(Long id);

	FindAuthRoleResult findAuthRole(FindAuthRoleDTO dto);

	FindRolesResult findRolesByCondition(FindRolesDTO dto);

}
