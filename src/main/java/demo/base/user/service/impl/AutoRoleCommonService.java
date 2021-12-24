package demo.base.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.impl.SystemCommonService;
import demo.base.user.mapper.AuthRoleMapper;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;

public abstract class AutoRoleCommonService extends SystemCommonService {

	@Autowired
	protected AuthRoleMapper authRoleMapper;
	
	@Autowired
	protected RoleService roleService;
	@Autowired
	protected AuthService authService;
	
}
