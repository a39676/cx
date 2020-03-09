package demo.base.user.service;

import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindRolesResult;
import demo.base.user.pojo.result.FindRolesVOResult;
import demo.base.user.pojo.type.RolesType;

public interface RoleService {

	void __initBaseRole();

	Roles getBaseRoleByName(String roleName);

	FindRolesResult getRolesByCondition(FindRolesDTO dto);

	FindRolesVOResult findRolesVO(FindRolesDTO dto);

	FindRolesVOResult findSysRoles();

	FindRolesVOResult findOrgRoles();

	RolesType findRolesType(Roles role);

	Roles findRoleByName(String roleName);

}