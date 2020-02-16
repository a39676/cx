package demo.base.user.service;

import java.util.List;

import demo.base.user.pojo.dto.FindRolesDTO;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.result.FindRolesResult;

public interface RoleService {

	void __initBaseRole();

	List<Roles> getRolesByOrgId(Long orgId);

	Roles getBaseRoleByName(String roleName);

	FindRolesResult getRolesByCondition(FindRolesDTO dto);

}