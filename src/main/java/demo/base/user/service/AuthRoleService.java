package demo.base.user.service;

import demo.base.user.pojo.dto.FindAuthRoleDTO;
import demo.base.user.pojo.result.FindAuthRoleResult;

public interface AuthRoleService {

	Long insertAuthRole(Long authId, Long roleId, Long creatorId);

	int deleteById(Long id);

	FindAuthRoleResult findAuthRole(FindAuthRoleDTO dto);

}
