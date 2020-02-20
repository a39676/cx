package demo.base.user.service;

import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindRolesResult;

public interface RoleService {

	void __initBaseRole();

	Roles getBaseRoleByName(String roleName);

	FindRolesResult getRolesByCondition(FindRolesDTO dto);

}